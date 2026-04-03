package com.spring_batch.RestErp.commun.batch.processor;

import com.spring_batch.RestErp.commun.dto.dim.DimDepartment;
import com.spring_batch.RestErp.commun.dto.source.DepartmentSource;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Configuration
public class  DepartmentProcessorConfig {

    @Bean
    public ItemProcessor<DepartmentSource, DimDepartment> departmentProcessor() {

        /*
         * Sert à éliminer les doublons pendant l'exécution du step.
         * La clé utilisée sera : companyId + departmentName normalisé
         */
        Set<String> seenDepartments = new HashSet<>();

        return source -> {

            // 1) Sécurité : ignorer une ligne null
            if (source == null) {
                return null;
            }

            // 2) Récupérer le nom brut du département
            String rawDepartmentName = source.getDepartmentName();

            // 3) Ignorer les noms null
            if (rawDepartmentName == null) {
                return null;
            }

            /*
             * 4) Nettoyage du nom :
             * - trim() : supprime espaces début/fin
             * - replaceAll("\\s+", " ") : remplace espaces multiples par un seul
             */
            String cleanedDepartmentName = rawDepartmentName
                    .trim()
                    .replaceAll("\\s+", " ");

            // 5) Ignorer si le nom est vide après nettoyage
            if (cleanedDepartmentName.isEmpty()) {
                return null;
            }

            /*
             * 6) Normalisation pour détection des doublons
             * Exemple :
             * " RH ", "rh", "Rh" -> "rh"
             */
            String normalizedDepartmentName = cleanedDepartmentName.toLowerCase(Locale.ROOT);

            /*
             * 7) Clé de déduplication
             * Important : on déduplique dans le contexte de la même entreprise
             */
            String dedupKey = String.valueOf(source.getCompanyId()) + "|" + normalizedDepartmentName;

            // 8) Si déjà vu, on ignore la ligne
            if (seenDepartments.contains(dedupKey)) {
                return null;
            }

            // 9) Sinon on mémorise la clé comme déjà traitée
            seenDepartments.add(dedupKey);

            /*
             * 10) Construction de l'objet cible DW
             * companyKey sera rempli plus tard dans le writer via lookup sur dim_company
             */
            DimDepartment target = new DimDepartment();
            target.setDepartmentId(source.getDepartmentId());
            target.setCompanyId(source.getCompanyId());
            target.setCompanyKey(null);
            target.setDepartmentName(cleanedDepartmentName);
            target.setSupervisorId(source.getSupervisorId());
            target.setSupervisorName(source.getSupervisorName());
            target.setActive(source.getActive());

            /*
             * 11) Champs SCD Type 2
             * - effectiveFrom = date de chargement
             * - effectiveTo = null car ligne courante
             * - isCurrent = true
             */
            target.setEffectiveFrom(LocalDateTime.now());
            target.setEffectiveTo(null);
            target.setIsCurrent(true);

            return target;
        };
    }
}
