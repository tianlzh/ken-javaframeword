package com.shine.sourceflow.dao.show.strategy;

import com.shine.framework.utils.DateUtil;
import com.shine.framework.utils.TableUtil;
import com.shine.sourceflow.model.show.ShowGenericDto;

public class AppTrafficStdQueryStrategy implements IQueryStrategy {
	@Override
	public String[] createDailyQuerySQL(ShowGenericDto dto) {
		String inSql = this.createGenericSql(dto, 
				TableUtil.getMonthTable(dto.getDate()), "a.dst_ip",
				DateUtil.getDetailTime(DateUtil.getDateBegin(
				DateUtil.stringToDate(dto.getDate(),
				DateUtil.DATE_PATTERN_DEFAULT))), 
				DateUtil.getDetailTime(DateUtil.getDateEnd(
				DateUtil.stringToDate(dto.getDate(), 
				DateUtil.DATE_PATTERN_DEFAULT))));
		
		String outSql = this.createGenericSql(dto, 
				TableUtil.getMonthTable(dto.getDate()), "a.src_ip",
				DateUtil.getDetailTime(DateUtil.getDateBegin(
				DateUtil.stringToDate(dto.getDate(),
				DateUtil.DATE_PATTERN_DEFAULT))), 
				DateUtil.getDetailTime(DateUtil.getDateEnd(
				DateUtil.stringToDate(dto.getDate(), 
				DateUtil.DATE_PATTERN_DEFAULT))));
		String[] sqls = {inSql, outSql};
		return sqls;
	}

	@Override
	public String createDailySumSQL(ShowGenericDto dto) {
		String sql = this.createGenericSumSql( 
				TableUtil.getMonthTable(dto.getDate()),
				DateUtil.getDetailTime(DateUtil.getDateBegin(
				DateUtil.stringToDate(dto.getDate(),
				DateUtil.DATE_PATTERN_DEFAULT))), 
				DateUtil.getDetailTime(DateUtil.getDateEnd(
				DateUtil.stringToDate(dto.getDate(), 
				DateUtil.DATE_PATTERN_DEFAULT))));
		return sql.toString();
	}

	@Override
	public String[] createHourlyQuerySQL(ShowGenericDto dto) {
		String inSql = this.createGenericSql(dto, 
				TableUtil.getMonthTable(dto.getDate()), "a.dst_ip",
				DateUtil.getDetailTime(DateUtil.getIntervalOfHour(-1)), 
				DateUtil.getDetailTime());
		
		String outSql = this.createGenericSql(dto, 
				TableUtil.getMonthTable(dto.getDate()), "a.src_ip",
				DateUtil.getDetailTime(DateUtil.getIntervalOfHour(-1)), 
				DateUtil.getDetailTime());
		String[] sqls = {inSql, outSql};
		return sqls;
	}
	
	private String createGenericSql(ShowGenericDto dto, 
			String tableName, String fCondition, String bBegin, String bEnd) {
		StringBuffer sql = new StringBuffer();
		sql.append("select b.app_id, b.app_alias, b.ip_address, sum(a.bytes) as bytes_total from ");
		sql.append(tableName);
		sql.append(" as a, app_config as b where ");
		sql.append(fCondition);
		sql.append(" = b.ip_address");
		sql.append(" and a.src_port = b.ip_port");
		sql.append(" and a.log_time between '");
		sql.append(bBegin);
		sql.append("' and '");
		sql.append(bEnd);
		sql.append("' group by b.app_alias, b.ip_address limit ");
		sql.append(dto.getTopPageN());
		return sql.toString();
	}

	@Override
	public String createHourlySumSQL(ShowGenericDto dto) {
		String sql = this.createGenericSumSql( 
				TableUtil.getMonthTable(dto.getDate()),
				DateUtil.getDetailTime(DateUtil.getIntervalOfHour(-1)), 
				DateUtil.getDetailTime());
		return sql.toString();
	}

	@Override
	public String[] createMonthlyQuerySQL(ShowGenericDto dto) {
		String inSql = this.createGenericSql(dto, 
				TableUtil.getMonthTable(dto.getDate()), "a.dst_ip",
				DateUtil.getDetailTime(DateUtil.getMonthBegin(
				DateUtil.stringToDate(dto.getDate(),
				DateUtil.DATE_PATTERN_DEFAULT))), 
				DateUtil.getDetailTime(DateUtil.getMonthEnd(
				DateUtil.stringToDate(dto.getDate(), 
				DateUtil.DATE_PATTERN_DEFAULT))));
		
		String outSql = this.createGenericSql(dto, 
				TableUtil.getMonthTable(dto.getDate()), "a.src_ip",
				DateUtil.getDetailTime(DateUtil.getMonthBegin(
				DateUtil.stringToDate(dto.getDate(),
				DateUtil.DATE_PATTERN_DEFAULT))), 
				DateUtil.getDetailTime(DateUtil.getMonthEnd(
				DateUtil.stringToDate(dto.getDate(), 
				DateUtil.DATE_PATTERN_DEFAULT))));
		String[] sqls = {inSql, outSql};
		return sqls;
	}

	@Override
	public String createMonthlySumSQL(ShowGenericDto dto) {
		String sql = this.createGenericSumSql( 
				TableUtil.getMonthTable(dto.getDate()),
				DateUtil.getDetailTime(DateUtil.getMonthBegin(
				DateUtil.stringToDate(dto.getDate(),
				DateUtil.DATE_PATTERN_DEFAULT))), 
				DateUtil.getDetailTime(DateUtil.getMonthEnd(
				DateUtil.stringToDate(dto.getDate(), 
				DateUtil.DATE_PATTERN_DEFAULT))));
		return sql.toString();
	}

	@Override
	public String[] createWeeklyQuerySQL(ShowGenericDto dto) {
		String inSql = this.createGenericSql(dto, 
				TableUtil.getMonthTable(dto.getDate()), "a.dst_ip",
				DateUtil.getDetailTime(DateUtil.getWeekBegin(
				DateUtil.stringToDate(dto.getDate(),
				DateUtil.DATE_PATTERN_DEFAULT))), 
				DateUtil.getDetailTime(DateUtil.getWeekEnd(
				DateUtil.stringToDate(dto.getDate(), 
				DateUtil.DATE_PATTERN_DEFAULT))));
		
		String outSql = this.createGenericSql(dto, 
				TableUtil.getMonthTable(dto.getDate()), "a.src_ip",
				DateUtil.getDetailTime(DateUtil.getWeekBegin(
				DateUtil.stringToDate(dto.getDate(),
				DateUtil.DATE_PATTERN_DEFAULT))), 
				DateUtil.getDetailTime(DateUtil.getWeekEnd(
				DateUtil.stringToDate(dto.getDate(), 
				DateUtil.DATE_PATTERN_DEFAULT))));
		String[] sqls = {inSql, outSql};
		return sqls;
	}

	@Override
	public String createWeeklySumSQL(ShowGenericDto dto) {
		String sql = this.createGenericSumSql( 
				TableUtil.getMonthTable(dto.getDate()),
				DateUtil.getDetailTime(DateUtil.getWeekBegin(
				DateUtil.stringToDate(dto.getDate(),
				DateUtil.DATE_PATTERN_DEFAULT))), 
				DateUtil.getDetailTime(DateUtil.getWeekEnd(
				DateUtil.stringToDate(dto.getDate(), 
				DateUtil.DATE_PATTERN_DEFAULT))));
		return sql.toString();
	}
	
	private String createGenericSumSql(
			String tableName, String bBegin, String bEnd) {
		StringBuffer sql = new StringBuffer();
		sql.append("select sum(bytes) as bytes_sum from ");
		sql.append(tableName);
		sql.append(" where log_time between '");
		sql.append(bBegin);
		sql.append("' and '");
		sql.append(bEnd);
		sql.append("'");
		return sql.toString();
	}
}
