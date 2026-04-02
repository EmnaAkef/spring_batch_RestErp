package com.emna.spring_batch_test.commun.service;

import java.util.ArrayList;
import java.util.List;

import com.emna.spring_batch_test.commun.batch.reader.EmployeeReader;
import com.emna.spring_batch_test.commun.dto.dim.DimEmployee;
import com.emna.spring_batch_test.commun.dto.source.SourceEmployee;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.stereotype.Service;

@Service
public class EmployeeLoadService {

    private final TenantRegistryService tenantRegistryService;
    private final EmployeeReader employeeReader;
    private final ItemProcessor<SourceEmployee, DimEmployee> employeeProcessor;
    private final JdbcBatchItemWriter<DimEmployee> employeeWriter;

    public EmployeeLoadService(
            TenantRegistryService tenantRegistryService,
            EmployeeReader employeeReader,
            ItemProcessor<SourceEmployee, DimEmployee> employeeProcessor,
            JdbcBatchItemWriter<DimEmployee> employeeWriter) {
        this.tenantRegistryService = tenantRegistryService;
        this.employeeReader = employeeReader;
        this.employeeProcessor = employeeProcessor;
        this.employeeWriter = employeeWriter;
    }

    public void loadAllEmployees() throws Exception {
        System.out.println("=== START LOAD EMPLOYEES ===");

        List<TenantContext> tenants = tenantRegistryService.getActiveTenants();
        System.out.println("Nombre de tenants actifs trouvés: " + tenants.size());

        for (TenantContext tenant : tenants) {
            System.out.println("Traitement tenant: " + tenant.getTenantName() + " | schema=" + tenant.getSchemaName());

            List<SourceEmployee> sourceEmployees = employeeReader.readEmployees(tenant);
            System.out.println("Nombre d'employés lus: " + sourceEmployees.size());

            List<DimEmployee> targetEmployees = new ArrayList<>();

            for (SourceEmployee source : sourceEmployees) {
                DimEmployee target = employeeProcessor.process(source);
                if (target != null) {
                    targetEmployees.add(target);
                }
            }

            System.out.println("Nombre d'employés transformés: " + targetEmployees.size());

            if (!targetEmployees.isEmpty()) {
                employeeWriter.write(new Chunk<>(targetEmployees));
                System.out.println("Écriture terminée pour le tenant: " + tenant.getTenantName());
            } else {
                System.out.println("Aucune donnée à écrire pour le tenant: " + tenant.getTenantName());
            }
        }

        System.out.println("=== END LOAD EMPLOYEES ===");
    }
}