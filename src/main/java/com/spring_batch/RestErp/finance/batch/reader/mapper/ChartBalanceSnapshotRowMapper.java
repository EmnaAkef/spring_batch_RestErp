package com.spring_batch.RestErp.finance.batch.reader.mapper;

import com.spring_batch.RestErp.finance.dto.source.ChartBalanceSnapshotSource;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ChartBalanceSnapshotRowMapper implements RowMapper<ChartBalanceSnapshotSource> {

    @Override
    public ChartBalanceSnapshotSource mapRow(ResultSet rs, int rowNum) throws SQLException {
        ChartBalanceSnapshotSource item = new ChartBalanceSnapshotSource();

        item.setChartBalanceId(rs.getLong("chart_balance_id"));
        item.setCompanyId(rs.getLong("company_id"));
        item.setSchemaName(rs.getString("schema_name"));
        item.setChartId(rs.getLong("chart_id"));

        Timestamp ts = rs.getTimestamp("balance_timestamp");
        item.setTimestamp(ts != null ? ts.toLocalDateTime() : null);

        item.setCloseBalanceCredit((Double) rs.getObject("closebalancecredit"));
        item.setCloseBalanceDebit((Double) rs.getObject("closebalancedebit"));
        item.setOpenBalanceCredit((Double) rs.getObject("openbalancecredit"));
        item.setOpenBalanceDebit((Double) rs.getObject("openbalancedebit"));
        item.setTotalMovementCredit((Double) rs.getObject("totalmovementcredit"));
        item.setTotalMovementDebit((Double) rs.getObject("totalmovementdebit"));

        return item;
    }
}