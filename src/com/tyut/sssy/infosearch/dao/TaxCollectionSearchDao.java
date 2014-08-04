package com.tyut.sssy.infosearch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tyut.sssy.taxcollection.domain.FirmPayTaxChangeTable;
import com.tyut.sssy.taxcollection.domain.FirmPayTaxChangeTableParameter;
import com.tyut.sssy.taxcollection.domain.FirmTaxAccountTable;
import com.tyut.sssy.taxcollection.domain.FirmTaxAccountTableParameter;
import com.tyut.sssy.taxcollection.domain.OwnPayTaxDetailTable;
import com.tyut.sssy.taxcollection.domain.OwnPayTaxDetailTableParameter;
import com.tyut.sssy.taxcollection.domain.TaxCollectionFinishTable;
import com.tyut.sssy.taxcollection.domain.TaxCollectionFinishTableParameter;
import com.tyut.sssy.taxcollection.domain.TaxCollectionRealTimeSearchTable;
import com.tyut.sssy.taxcollection.domain.TaxCollectionStructureChangeTable;
import com.tyut.sssy.taxcollection.domain.TaxCollectionStructureChangeTableParameter;
import com.tyut.sssy.taxcollection.domain.ToResolveTaxBalanceTable;
import com.tyut.sssy.taxcollection.domain.ToResolveTaxBalanceTableParameter;
import com.tyut.sssy.taxcollection.domain.ToResolveTaxChangeTable;
import com.tyut.sssy.taxcollection.domain.ToResolveTaxChangeTableParameter;
import com.tyut.sssy.utils.db.C3P0Util;

public class TaxCollectionSearchDao {

	/**
	 * 收入完成情况表
	 * @param taxCollectionFinishTableParameter
	 * @return
	 */
	public List<TaxCollectionFinishTable> getFinishTable(
			TaxCollectionFinishTableParameter taxCollectionFinishTableParameter) {
		Connection conn = null;
        String sql = "";
        
        if (!taxCollectionFinishTableParameter.getType().equals("none")) {
        	// 本期排序
        	if (taxCollectionFinishTableParameter.getType().equals("a2")) {
        		if (taxCollectionFinishTableParameter.getOrder().equals("asc")) {
        		// 升序
        			sql = "select * from dss_sssr_wcqk (?, ?, ?, ?, ?, ?, ?, ?) order by a2 asc";
        		} else if (taxCollectionFinishTableParameter.getOrder().equals("desc")){
        		// 降序
        			sql = "select * from dss_sssr_wcqk (?, ?, ?, ?, ?, ?, ?, ?) order by a2 desc";
        		}
        	} else if (taxCollectionFinishTableParameter.getType().equals("a4")) {
        		// 本期增长排序
        		if (taxCollectionFinishTableParameter.getOrder().equals("asc")) {
        		// 升序
        			sql = "select * from dss_sssr_wcqk (?, ?, ?, ?, ?, ?, ?, ?) order by a4 asc";
        		} else if (taxCollectionFinishTableParameter.getOrder().equals("desc")){
        		// 降序
        			sql = "select * from dss_sssr_wcqk (?, ?, ?, ?, ?, ?, ?, ?) order by a4 desc";
        		}
        	} else if (taxCollectionFinishTableParameter.getType().equals("a5")) {
        		// 累计排序
        		if (taxCollectionFinishTableParameter.getOrder().equals("asc")) {
        		// 升序
        			sql = "select * from dss_sssr_wcqk (?, ?, ?, ?, ?, ?, ?, ?) order by a5 asc";
        		} else if (taxCollectionFinishTableParameter.getOrder().equals("desc")){
        		// 降序
        			sql = "select * from dss_sssr_wcqk (?, ?, ?, ?, ?, ?, ?, ?) order by a5 desc";
        		}
        	} else if (taxCollectionFinishTableParameter.getType().equals("a7")) {
        		// 累计增长 排序
        		if (taxCollectionFinishTableParameter.getOrder().equals("asc")) {
        		// 升序
        			sql = "select * from dss_sssr_wcqk (?, ?, ?, ?, ?, ?, ?, ?) order by a7 asc";
        		} else if (taxCollectionFinishTableParameter.getOrder().equals("desc")){
        		// 降序
        			sql = "select * from dss_sssr_wcqk (?, ?, ?, ?, ?, ?, ?, ?) order by a7 desc";
        		}
        	} else if (taxCollectionFinishTableParameter.getType().equals("a8")) {
        		// 占计划 排序
        		if (taxCollectionFinishTableParameter.getOrder().equals("asc")) {
        		// 升序
        			sql = "select * from dss_sssr_wcqk (?, ?, ?, ?, ?, ?, ?, ?) order by a8 asc";
        		} else if (taxCollectionFinishTableParameter.getOrder().equals("desc")){
        		// 降序
        			sql = "select * from dss_sssr_wcqk (?, ?, ?, ?, ?, ?, ?, ?) order by a8 desc";
        		}
        	}
        	
        } else {
        	// 不排序
        	sql = "select * from dss_sssr_wcqk (?, ?, ?, ?, ?, ?, ?, ?)";
        }
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<TaxCollectionFinishTable> tableList = new ArrayList<TaxCollectionFinishTable>();

        TaxCollectionFinishTable taxCollectionFinishTable = null;
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, taxCollectionFinishTableParameter.getSwjg());
            ps.setString(2, taxCollectionFinishTableParameter.getSjlx());
            ps.setString(3, taxCollectionFinishTableParameter.getNd());
            ps.setString(4, taxCollectionFinishTableParameter.getSqQ());
            ps.setString(5, taxCollectionFinishTableParameter.getSqZ());
            ps.setString(6, taxCollectionFinishTableParameter.getYf());
            ps.setString(7, taxCollectionFinishTableParameter.getPd());
            ps.setString(8, taxCollectionFinishTableParameter.getFl());

            rs = ps.executeQuery();

            while (rs.next()) {
            	taxCollectionFinishTable = new TaxCollectionFinishTable();
            	taxCollectionFinishTable.setXm(rs.getString("xm"));
            	taxCollectionFinishTable.setA1(rs.getBigDecimal("a1"));
            	taxCollectionFinishTable.setA2(rs.getBigDecimal("a2"));
            	taxCollectionFinishTable.setA3(rs.getBigDecimal("a3"));
            	taxCollectionFinishTable.setA4(rs.getBigDecimal("a4"));
            	taxCollectionFinishTable.setA5(rs.getBigDecimal("a5"));
            	taxCollectionFinishTable.setA6(rs.getBigDecimal("a6"));
            	taxCollectionFinishTable.setA7(rs.getBigDecimal("a7"));
            	taxCollectionFinishTable.setA8(rs.getBigDecimal("a8"));
            	taxCollectionFinishTable.setFl(taxCollectionFinishTableParameter.getFl());
            	
            	tableList.add(taxCollectionFinishTable);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return tableList;
	}

	/**
	 * 税收收入结构变化表
	 * @param taxCollectionStructureChangeTableParameter
	 * @return
	 */
	public List<TaxCollectionStructureChangeTable> getStructureChangeTable(
			TaxCollectionStructureChangeTableParameter taxCollectionStructureChangeTableParameter) {
		Connection conn = null;
        String sql = "";
        
        if (!taxCollectionStructureChangeTableParameter.getType().equals("none")) {
        	// 排序
        	if (taxCollectionStructureChangeTableParameter.getType().equals("a1")) {
        		// 本期累计 排序
        		if (taxCollectionStructureChangeTableParameter.getOrder().equals("asc")) {
        			// 升序
        			sql = "select * from dss_sssr_jgbh (?, ?, ?, ?, ?, ?) order by a1 asc";
        		} else if (taxCollectionStructureChangeTableParameter.getOrder().equals("desc")) {
        			// 降序
        			sql = "select * from dss_sssr_jgbh (?, ?, ?, ?, ?, ?) order by a1 desc";
        		}
        	} else if (taxCollectionStructureChangeTableParameter.getType().equals("a2")) {
        		// 占总量比重 排序
        		if (taxCollectionStructureChangeTableParameter.getOrder().equals("asc")) {
        			// 升序
        			sql = "select * from dss_sssr_jgbh (?, ?, ?, ?, ?, ?) order by a2 asc";
        		} else if (taxCollectionStructureChangeTableParameter.getOrder().equals("desc")) {
        			// 降序
        			sql = "select * from dss_sssr_jgbh (?, ?, ?, ?, ?, ?) order by a2 desc";
        		}
        	} else if (taxCollectionStructureChangeTableParameter.getType().equals("a4")) {
        		// 同期占总量比重 排序
        		if (taxCollectionStructureChangeTableParameter.getOrder().equals("asc")) {
        			// 升序
        			sql = "select * from dss_sssr_jgbh (?, ?, ?, ?, ?, ?) order by a4 asc";
        		} else if (taxCollectionStructureChangeTableParameter.getOrder().equals("desc")) {
        			// 降序
        			sql = "select * from dss_sssr_jgbh (?, ?, ?, ?, ?, ?) order by a4 desc";
        		}
        	} else if (taxCollectionStructureChangeTableParameter.getType().equals("a5")) {
        		// 比重变动率 排序
        		if (taxCollectionStructureChangeTableParameter.getOrder().equals("asc")) {
        			// 升序
        			sql = "select * from dss_sssr_jgbh (?, ?, ?, ?, ?, ?) order by a5 asc";
        		} else if (taxCollectionStructureChangeTableParameter.getOrder().equals("desc")) {
        			// 降序
        			sql = "select * from dss_sssr_jgbh (?, ?, ?, ?, ?, ?) order by a5 desc";
        		}
        	}
        } else {
        	//排序
        	sql = "select * from dss_sssr_jgbh (?, ?, ?, ?, ?, ?)";
        }
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<TaxCollectionStructureChangeTable> tableList = new ArrayList<TaxCollectionStructureChangeTable>();

        TaxCollectionStructureChangeTable taxCollectionStructureChangeTable = null;
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, taxCollectionStructureChangeTableParameter.getSwjg());
            ps.setString(2, taxCollectionStructureChangeTableParameter.getSjlx());
            ps.setString(3, taxCollectionStructureChangeTableParameter.getNd());
            ps.setString(4, taxCollectionStructureChangeTableParameter.getPd());
            ps.setString(5, taxCollectionStructureChangeTableParameter.getFl());
            ps.setString(6, taxCollectionStructureChangeTableParameter.getYf());

            rs = ps.executeQuery();

            while (rs.next()) {
            	taxCollectionStructureChangeTable = new TaxCollectionStructureChangeTable();
            	taxCollectionStructureChangeTable.setXm(rs.getString("xm"));
            	taxCollectionStructureChangeTable.setA1(rs.getBigDecimal("a1"));
            	taxCollectionStructureChangeTable.setA2(rs.getBigDecimal("a2"));
            	taxCollectionStructureChangeTable.setA3(rs.getBigDecimal("a3"));
            	taxCollectionStructureChangeTable.setA4(rs.getBigDecimal("a4"));
            	taxCollectionStructureChangeTable.setA5(rs.getBigDecimal("a5"));
            	taxCollectionStructureChangeTable.setFl(taxCollectionStructureChangeTableParameter.getFl());
            	
            	tableList.add(taxCollectionStructureChangeTable);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return tableList;
	}

	/**
	 * 待解税金余额表
	 * @param toResolveBalanceTableParameter
	 * @return
	 */
	public List<ToResolveTaxBalanceTable> getToResolveBalanceTable(
			ToResolveTaxBalanceTableParameter toResolveBalanceTableParameter) {
		
		Connection conn = null;
        String sql = "";
        
        if (!toResolveBalanceTableParameter.getType().equals("none")) {
        	// 营业税 排序
        	if (toResolveBalanceTableParameter.getType().equals("a1")) {
        		if (toResolveBalanceTableParameter.getOrder().equals("asc")) {
        		// 升序
        			sql = "select * from dss_djsjye (?, ?, ?, ?, ?) order by a1 asc";
        		} else if (toResolveBalanceTableParameter.getOrder().equals("desc")){
        		// 降序
        			sql = "select * from dss_djsjye (?, ?, ?, ?, ?) order by a1 desc";
        		}
        	} else if (toResolveBalanceTableParameter.getType().equals("a2")) {
        		// 企业所得税 排序
        		if (toResolveBalanceTableParameter.getOrder().equals("asc")) {
        		// 升序
        			sql = "select * from dss_djsjye (?, ?, ?, ?, ?) order by a2 asc";
        		} else if (toResolveBalanceTableParameter.getOrder().equals("desc")){
        		// 降序
        			sql = "select * from dss_djsjye (?, ?, ?, ?, ?) order by a2 desc";
        		}
        	}
        } else {
        	// 不排序
        	sql = "select * from dss_djsjye (?, ?, ?, ?, ?)";
        }
		
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ToResolveTaxBalanceTable> tableList = new ArrayList<ToResolveTaxBalanceTable>();

        ToResolveTaxBalanceTable toResolveBalanceTable = null;
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, toResolveBalanceTableParameter.getXsxm());
            ps.setString(2, toResolveBalanceTableParameter.getSwjgDm());
            ps.setString(3, toResolveBalanceTableParameter.getNd());
            ps.setString(4, toResolveBalanceTableParameter.getSqZ());
            ps.setString(5, toResolveBalanceTableParameter.getNsrsbh());

            rs = ps.executeQuery();

            while (rs.next()) {
            	toResolveBalanceTable = new ToResolveTaxBalanceTable();
            	toResolveBalanceTable.setXm(rs.getString("xm"));
            	toResolveBalanceTable.setA1(rs.getBigDecimal("a1"));
            	toResolveBalanceTable.setA2(rs.getBigDecimal("a2"));
            	toResolveBalanceTable.setA3(rs.getBigDecimal("a3"));
            	toResolveBalanceTable.setA4(rs.getBigDecimal("a4"));
            	toResolveBalanceTable.setA5(rs.getBigDecimal("a5"));
            	toResolveBalanceTable.setA6(rs.getBigDecimal("a6"));
            	toResolveBalanceTable.setA7(rs.getBigDecimal("a7"));
            	toResolveBalanceTable.setA8(rs.getBigDecimal("a8"));
            	toResolveBalanceTable.setA9(rs.getBigDecimal("a9"));
            	toResolveBalanceTable.setA10(rs.getBigDecimal("a10"));
            	toResolveBalanceTable.setA11(rs.getBigDecimal("a11"));
            	toResolveBalanceTable.setXsxm(toResolveBalanceTableParameter.getXsxm());
            	
            	tableList.add(toResolveBalanceTable);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return tableList;
	}

	/**
	 * 待解税金变动情况表
	 * @param toResolveTaxChangeTableParameter
	 * @return
	 */
	public List<ToResolveTaxChangeTable> getToResolveChangeTable(
			ToResolveTaxChangeTableParameter toResolveTaxChangeTableParameter) {
		Connection conn = null;
        String sql = "";
        
        if (!toResolveTaxChangeTableParameter.getType().equals("none")) {
        	// a1 排序
        	if (toResolveTaxChangeTableParameter.getType().equals("a1")) {
        		if (toResolveTaxChangeTableParameter.getOrder().equals("asc")) {
        		// 升序
        			sql = "select * from dss_djsj_bdqk (?, ?, ?, ?) order by a1 asc";
        		} else if (toResolveTaxChangeTableParameter.getOrder().equals("desc")){
        		// 降序
        			sql = "select * from dss_djsj_bdqk (?, ?, ?, ?) order by a1 desc";
        		}
        	} else if (toResolveTaxChangeTableParameter.getType().equals("a3")) {
        		// a3 排序
        		if (toResolveTaxChangeTableParameter.getOrder().equals("asc")) {
        		// 升序
        			sql = "select * from dss_djsj_bdqk (?, ?, ?, ?) order by a3 asc";
        		} else if (toResolveTaxChangeTableParameter.getOrder().equals("desc")){
        		// 降序
        			sql = "select * from dss_djsj_bdqk (?, ?, ?, ?) order by a3 desc";
        		}
        	} else if (toResolveTaxChangeTableParameter.getType().equals("a4")) {
        		// a3 排序
        		if (toResolveTaxChangeTableParameter.getOrder().equals("asc")) {
        		// 升序
        			sql = "select * from dss_djsj_bdqk (?, ?, ?, ?) order by a4 asc";
        		} else if (toResolveTaxChangeTableParameter.getOrder().equals("desc")){
        		// 降序
        			sql = "select * from dss_djsj_bdqk (?, ?, ?, ?) order by a4 desc";
        		}
        	} else if (toResolveTaxChangeTableParameter.getType().equals("a6")) {
        		// a3 排序
        		if (toResolveTaxChangeTableParameter.getOrder().equals("asc")) {
        		// 升序
        			sql = "select * from dss_djsj_bdqk (?, ?, ?, ?) order by a6 asc";
        		} else if (toResolveTaxChangeTableParameter.getOrder().equals("desc")){
        		// 降序
        			sql = "select * from dss_djsj_bdqk (?, ?, ?, ?) order by a6 desc";
        		}
        	} else if (toResolveTaxChangeTableParameter.getType().equals("a7")) {
        		// a3 排序
        		if (toResolveTaxChangeTableParameter.getOrder().equals("asc")) {
        		// 升序
        			sql = "select * from dss_djsj_bdqk (?, ?, ?, ?) order by a7 asc";
        		} else if (toResolveTaxChangeTableParameter.getOrder().equals("desc")){
        		// 降序
        			sql = "select * from dss_djsj_bdqk (?, ?, ?, ?) order by a7 desc";
        		}
        	} else if (toResolveTaxChangeTableParameter.getType().equals("a9")) {
        		// a3 排序
        		if (toResolveTaxChangeTableParameter.getOrder().equals("asc")) {
        		// 升序
        			sql = "select * from dss_djsj_bdqk (?, ?, ?, ?) order by a9 asc";
        		} else if (toResolveTaxChangeTableParameter.getOrder().equals("desc")){
        		// 降序
        			sql = "select * from dss_djsj_bdqk (?, ?, ?, ?) order by a9 desc";
        		}
        	} else if (toResolveTaxChangeTableParameter.getType().equals("a10")) {
        		// a3 排序
        		if (toResolveTaxChangeTableParameter.getOrder().equals("asc")) {
        		// 升序
        			sql = "select * from dss_djsj_bdqk (?, ?, ?, ?) order by a10 asc";
        		} else if (toResolveTaxChangeTableParameter.getOrder().equals("desc")){
        		// 降序
        			sql = "select * from dss_djsj_bdqk (?, ?, ?, ?) order by a10 desc";
        		}
        	} else if (toResolveTaxChangeTableParameter.getType().equals("a12")) {
        		// a3 排序
        		if (toResolveTaxChangeTableParameter.getOrder().equals("asc")) {
        		// 升序
        			sql = "select * from dss_djsj_bdqk (?, ?, ?, ?) order by a12 asc";
        		} else if (toResolveTaxChangeTableParameter.getOrder().equals("desc")){
        		// 降序
        			sql = "select * from dss_djsj_bdqk (?, ?, ?, ?) order by a12 desc";
        		}
        	}
        } else {
        	// 不排序
        	sql = "select * from dss_djsj_bdqk (?, ?, ?, ?)";
        }
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ToResolveTaxChangeTable> tableList = new ArrayList<ToResolveTaxChangeTable>();

        ToResolveTaxChangeTable toResolveTaxChangeTable = null;
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, toResolveTaxChangeTableParameter.getSwjg());
            ps.setString(2, toResolveTaxChangeTableParameter.getNd());
            ps.setString(3, toResolveTaxChangeTableParameter.getSqZ());
            ps.setString(4, toResolveTaxChangeTableParameter.getNsrsbh());

            rs = ps.executeQuery();

            while (rs.next()) {
            	toResolveTaxChangeTable = new ToResolveTaxChangeTable();
            	toResolveTaxChangeTable.setXm(rs.getString("xm"));
            	toResolveTaxChangeTable.setA1(rs.getBigDecimal("a1"));
            	toResolveTaxChangeTable.setA2(rs.getBigDecimal("a2"));
            	toResolveTaxChangeTable.setA3(rs.getBigDecimal("a3"));
            	toResolveTaxChangeTable.setA4(rs.getBigDecimal("a4"));
            	toResolveTaxChangeTable.setA5(rs.getBigDecimal("a5"));
            	toResolveTaxChangeTable.setA6(rs.getBigDecimal("a6"));
            	toResolveTaxChangeTable.setA7(rs.getBigDecimal("a7"));
            	toResolveTaxChangeTable.setA8(rs.getBigDecimal("a8"));
            	toResolveTaxChangeTable.setA9(rs.getBigDecimal("a9"));
            	toResolveTaxChangeTable.setA10(rs.getBigDecimal("a10"));
            	toResolveTaxChangeTable.setA11(rs.getBigDecimal("a11"));
            	toResolveTaxChangeTable.setA12(rs.getBigDecimal("a12"));
            	
            	tableList.add(toResolveTaxChangeTable);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return tableList;
	}

	/**
	 * 欠缴税金明细表
	 * @param ownPayTaxDetailTableParameter
	 * @return
	 */
	public List<OwnPayTaxDetailTable> getOwnPayTaxDetailTable(
			OwnPayTaxDetailTableParameter ownPayTaxDetailTableParameter) {
		
		Connection conn = null;
        String sql = "";
        
        if (!ownPayTaxDetailTableParameter.getType().equals("none")) {
        	// a1 排序
        	if (ownPayTaxDetailTableParameter.getType().equals("a1")) {
        		if (ownPayTaxDetailTableParameter.getOrder().equals("asc")) {
        		// 升序
        			sql = "select * from dss_qjsj_mxb (?, ?, ?, ?, ?) order by a1 asc";
        		} else if (ownPayTaxDetailTableParameter.getOrder().equals("desc")){
        		// 降序
        			sql = "select * from dss_qjsj_mxb (?, ?, ?, ?, ?) order by a1 desc";
        		}
        	} else if (ownPayTaxDetailTableParameter.getType().equals("a2")) {
        		if (ownPayTaxDetailTableParameter.getOrder().equals("asc")) {
        		// 升序
        			sql = "select * from dss_qjsj_mxb (?, ?, ?, ?, ?) order by a2 asc";
        		} else if (ownPayTaxDetailTableParameter.getOrder().equals("desc")){
        		// 降序
        			sql = "select * from dss_qjsj_mxb (?, ?, ?, ?, ?) order by a2 desc";
        		}
        	} else if (ownPayTaxDetailTableParameter.getType().equals("a3")) {
        		if (ownPayTaxDetailTableParameter.getOrder().equals("asc")) {
        		// 升序
        			sql = "select * from dss_qjsj_mxb (?, ?, ?, ?, ?) order by a3 asc";
        		} else if (ownPayTaxDetailTableParameter.getOrder().equals("desc")){
        		// 降序
        			sql = "select * from dss_qjsj_mxb (?, ?, ?, ?, ?) order by a3 desc";
        		}
        	} else if (ownPayTaxDetailTableParameter.getType().equals("a4")) {
        		if (ownPayTaxDetailTableParameter.getOrder().equals("asc")) {
        		// 升序
        			sql = "select * from dss_qjsj_mxb (?, ?, ?, ?, ?) order by a4 asc";
        		} else if (ownPayTaxDetailTableParameter.getOrder().equals("desc")){
        		// 降序
        			sql = "select * from dss_qjsj_mxb (?, ?, ?, ?, ?) order by a4 desc";
        		}
        	}
        } else {
        	// 不排序
        	sql = "select * from dss_qjsj_mxb (?, ?, ?, ?, ?)";
        }
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<OwnPayTaxDetailTable> tableList = new ArrayList<OwnPayTaxDetailTable>();

        OwnPayTaxDetailTable ownPayTaxDetailTable = null;
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, ownPayTaxDetailTableParameter.getFl());
            ps.setString(2, ownPayTaxDetailTableParameter.getSwjg());
            ps.setString(3, ownPayTaxDetailTableParameter.getNd());
            ps.setString(4, ownPayTaxDetailTableParameter.getSqZ());
            ps.setString(5, ownPayTaxDetailTableParameter.getNsrsbh());

            rs = ps.executeQuery();

            while (rs.next()) {
            	ownPayTaxDetailTable = new OwnPayTaxDetailTable();
            	ownPayTaxDetailTable.setXm(rs.getString("xm"));
            	ownPayTaxDetailTable.setA1(rs.getBigDecimal("a1"));
            	ownPayTaxDetailTable.setA2(rs.getBigDecimal("a2"));
            	ownPayTaxDetailTable.setA3(rs.getBigDecimal("a3"));
            	ownPayTaxDetailTable.setA4(rs.getBigDecimal("a4"));
            	ownPayTaxDetailTable.setFl(ownPayTaxDetailTableParameter.getFl());
            	
            	tableList.add(ownPayTaxDetailTable);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return tableList;
	}

	/**
	 * 重点企业实缴税收变化表
	 * @param firmPayTaxChangeTableParameter
	 * @return
	 */
	public List<FirmPayTaxChangeTable> getFirmPayTaxChangeTable(
			FirmPayTaxChangeTableParameter firmPayTaxChangeTableParameter) {
		Connection conn = null;
        String sql = "";
        
        if (!firmPayTaxChangeTableParameter.getType().equals("none")) {
        	// a1 排序
        	if (firmPayTaxChangeTableParameter.getType().equals("a1")) {
        		if (firmPayTaxChangeTableParameter.getOrder().equals("asc")) {
        		// 升序
        			sql = "select * from dss_zdqy_sjss (?, ?, ?, ?, ?) order by a1 asc";
        		} else if (firmPayTaxChangeTableParameter.getOrder().equals("desc")){
        		// 降序
        			sql = "select * from dss_zdqy_sjss (?, ?, ?, ?, ?) order by a1 desc";
        		}
        	} else if (firmPayTaxChangeTableParameter.getType().equals("a3")) {
        		if (firmPayTaxChangeTableParameter.getOrder().equals("asc")) {
        		// 升序
        			sql = "select * from dss_zdqy_sjss (?, ?, ?, ?, ?) order by a3 asc";
        		} else if (firmPayTaxChangeTableParameter.getOrder().equals("desc")){
        		// 降序
        			sql = "select * from dss_zdqy_sjss (?, ?, ?, ?, ?) order by a3 desc";
        		}
        	} else if (firmPayTaxChangeTableParameter.getType().equals("a4")) {
        		if (firmPayTaxChangeTableParameter.getOrder().equals("asc")) {
        		// 升序
        			sql = "select * from dss_zdqy_sjss (?, ?, ?, ?, ?) order by a4 asc";
        		} else if (firmPayTaxChangeTableParameter.getOrder().equals("desc")){
        		// 降序
        			sql = "select * from dss_zdqy_sjss (?, ?, ?, ?, ?) order by a4 desc";
        		}
        	} else if (firmPayTaxChangeTableParameter.getType().equals("a5")) {
        		if (firmPayTaxChangeTableParameter.getOrder().equals("asc")) {
        		// 升序
        			sql = "select * from dss_zdqy_sjss (?, ?, ?, ?, ?) order by a5 asc";
        		} else if (firmPayTaxChangeTableParameter.getOrder().equals("desc")){
        		// 降序
        			sql = "select * from dss_zdqy_sjss (?, ?, ?, ?, ?) order by a5 desc";
        		}
        	} else if (firmPayTaxChangeTableParameter.getType().equals("a7")) {
        		if (firmPayTaxChangeTableParameter.getOrder().equals("asc")) {
        		// 升序
        			sql = "select * from dss_zdqy_sjss (?, ?, ?, ?, ?) order by a7 asc";
        		} else if (firmPayTaxChangeTableParameter.getOrder().equals("desc")){
        		// 降序
        			sql = "select * from dss_zdqy_sjss (?, ?, ?, ?, ?) order by a7 desc";
        		}
        	} else if (firmPayTaxChangeTableParameter.getType().equals("a8")) {
        		if (firmPayTaxChangeTableParameter.getOrder().equals("asc")) {
        		// 升序
        			sql = "select * from dss_zdqy_sjss (?, ?, ?, ?, ?) order by a8 asc";
        		} else if (firmPayTaxChangeTableParameter.getOrder().equals("desc")){
        		// 降序
        			sql = "select * from dss_zdqy_sjss (?, ?, ?, ?, ?) order by a8 desc";
        		}
        	}
        } else {
        	// 不排序
        	sql = "select * from dss_zdqy_sjss (?, ?, ?, ?, ?)";
        }
		
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<FirmPayTaxChangeTable> tableList = new ArrayList<FirmPayTaxChangeTable>();

        FirmPayTaxChangeTable firmPayTaxChangeTable = null;
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, firmPayTaxChangeTableParameter.getHy());
            ps.setString(2, firmPayTaxChangeTableParameter.getSwjg());
            ps.setString(3, firmPayTaxChangeTableParameter.getNd());
            ps.setString(4, firmPayTaxChangeTableParameter.getSqZ());
            ps.setString(5, firmPayTaxChangeTableParameter.getNsrsbh());

            rs = ps.executeQuery();

            while (rs.next()) {
            	firmPayTaxChangeTable = new FirmPayTaxChangeTable();
            	firmPayTaxChangeTable.setXm(rs.getString("xm"));
            	firmPayTaxChangeTable.setA1(rs.getBigDecimal("a1"));
            	firmPayTaxChangeTable.setA2(rs.getBigDecimal("a2"));
            	firmPayTaxChangeTable.setA3(rs.getBigDecimal("a3"));
            	firmPayTaxChangeTable.setA4(rs.getBigDecimal("a4"));
            	firmPayTaxChangeTable.setA5(rs.getBigDecimal("a5"));
            	firmPayTaxChangeTable.setA6(rs.getBigDecimal("a6"));
            	firmPayTaxChangeTable.setA7(rs.getBigDecimal("a7"));
            	firmPayTaxChangeTable.setA8(rs.getBigDecimal("a8"));
            	
            	tableList.add(firmPayTaxChangeTable);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return tableList;
	}

	/**
	 * 企业税收台帐
	 * @param firmTaxAccountTableParameter
	 * @return
	 */
	public List<FirmTaxAccountTable> getFirmTaxAccountTable(
			FirmTaxAccountTableParameter firmTaxAccountTableParameter) {
		Connection conn = null;
        String sql = "";
        
        if (!firmTaxAccountTableParameter.getType().equals("none")) {
        	// a11 排序
        	if (firmTaxAccountTableParameter.getType().equals("a11")) {
        		if (firmTaxAccountTableParameter.getOrder().equals("asc")) {
        		// 升序
        			sql = "select * from dss_qysstz (?, ?, ?, ?, ?) order by a11 asc";
        		} else if (firmTaxAccountTableParameter.getOrder().equals("desc")){
        		// 降序
        			sql = "select * from dss_qysstz (?, ?, ?, ?, ?) order by a11 desc";
        		}
        	} else if (firmTaxAccountTableParameter.getType().equals("a12")) {
        		if (firmTaxAccountTableParameter.getOrder().equals("asc")) {
        		// 升序
        			sql = "select * from dss_qysstz (?, ?, ?, ?, ?) order by a12 asc";
        		} else if (firmTaxAccountTableParameter.getOrder().equals("desc")){
        		// 降序
        			sql = "select * from dss_qysstz (?, ?, ?, ?, ?) order by a12 desc";
        		}
        	} else if (firmTaxAccountTableParameter.getType().equals("a13")) {
        		if (firmTaxAccountTableParameter.getOrder().equals("asc")) {
        		// 升序
        			sql = "select * from dss_qysstz (?, ?, ?, ?, ?) order by a13 asc";
        		} else if (firmTaxAccountTableParameter.getOrder().equals("desc")){
        		// 降序
        			sql = "select * from dss_qysstz (?, ?, ?, ?, ?) order by a13 desc";
        		}
        	}
        } else {
        	// 不排序
        	sql = "select * from dss_qysstz (?, ?, ?, ?, ?)";
        }
		
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<FirmTaxAccountTable> tableList = new ArrayList<FirmTaxAccountTable>();

        FirmTaxAccountTable firmTaxAccountTable = null;
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, firmTaxAccountTableParameter.getHy());
            ps.setString(2, firmTaxAccountTableParameter.getQyzclx());
            ps.setString(3, firmTaxAccountTableParameter.getSwjg());
            ps.setString(4, firmTaxAccountTableParameter.getNd());
            ps.setString(5, firmTaxAccountTableParameter.getSqZ());

            rs = ps.executeQuery();

            while (rs.next()) {
            	firmTaxAccountTable = new FirmTaxAccountTable();
            	firmTaxAccountTable.setXm(rs.getString("xm"));
            	firmTaxAccountTable.setA11(rs.getBigDecimal("a11"));
            	firmTaxAccountTable.setA12(rs.getBigDecimal("a12"));
            	firmTaxAccountTable.setA13(rs.getBigDecimal("a13"));
            	firmTaxAccountTable.setA21(rs.getBigDecimal("a21"));
            	firmTaxAccountTable.setA22(rs.getBigDecimal("a22"));
            	firmTaxAccountTable.setA23(rs.getBigDecimal("a23"));
            	firmTaxAccountTable.setA31(rs.getBigDecimal("a31"));
            	firmTaxAccountTable.setA32(rs.getBigDecimal("a32"));
            	firmTaxAccountTable.setA33(rs.getBigDecimal("a33"));
            	firmTaxAccountTable.setA41(rs.getBigDecimal("a41"));
            	firmTaxAccountTable.setA42(rs.getBigDecimal("a42"));
            	firmTaxAccountTable.setA43(rs.getBigDecimal("a43"));
            	firmTaxAccountTable.setA51(rs.getBigDecimal("a51"));
            	firmTaxAccountTable.setA52(rs.getBigDecimal("a52"));
            	firmTaxAccountTable.setA53(rs.getBigDecimal("a53"));
            	firmTaxAccountTable.setA61(rs.getBigDecimal("a61"));
            	firmTaxAccountTable.setA62(rs.getBigDecimal("a62"));
            	firmTaxAccountTable.setA63(rs.getBigDecimal("a63"));
            	firmTaxAccountTable.setA71(rs.getBigDecimal("a71"));
            	firmTaxAccountTable.setA72(rs.getBigDecimal("a72"));
            	firmTaxAccountTable.setA73(rs.getBigDecimal("a73"));
            	firmTaxAccountTable.setA81(rs.getBigDecimal("a81"));
            	firmTaxAccountTable.setA82(rs.getBigDecimal("a82"));
            	firmTaxAccountTable.setA83(rs.getBigDecimal("a83"));
            	firmTaxAccountTable.setA91(rs.getBigDecimal("a91"));
            	firmTaxAccountTable.setA92(rs.getBigDecimal("a92"));
            	firmTaxAccountTable.setA93(rs.getBigDecimal("a93"));
            	firmTaxAccountTable.setA101(rs.getBigDecimal("a101"));
            	firmTaxAccountTable.setA102(rs.getBigDecimal("a102"));
            	firmTaxAccountTable.setA103(rs.getBigDecimal("a103"));
            	firmTaxAccountTable.setA111(rs.getBigDecimal("a111"));
            	firmTaxAccountTable.setA112(rs.getBigDecimal("a112"));
            	firmTaxAccountTable.setA113(rs.getBigDecimal("a113"));
            	firmTaxAccountTable.setA121(rs.getBigDecimal("a121"));
            	firmTaxAccountTable.setA122(rs.getBigDecimal("a122"));
            	firmTaxAccountTable.setA123(rs.getBigDecimal("a123"));
            	firmTaxAccountTable.setA131(rs.getBigDecimal("a131"));
            	firmTaxAccountTable.setA132(rs.getBigDecimal("a132"));
            	firmTaxAccountTable.setA133(rs.getBigDecimal("a133"));
            	
            	tableList.add(firmTaxAccountTable);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return tableList;
	}

	/**
	 * 税收收入实时入库查询
	 * @param nd
	 * @return
	 */
	public List<TaxCollectionRealTimeSearchTable> TaxCollectionRealTimeSearchTable(
			String nd) {
		Connection conn = null;
        String sql = "select * from dss_sssr_ssrk (?)";
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<TaxCollectionRealTimeSearchTable> tableList = new ArrayList<TaxCollectionRealTimeSearchTable>();

        TaxCollectionRealTimeSearchTable taxCollectionRealTimeSearchTable = null;
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, nd);

            rs = ps.executeQuery();

            while (rs.next()) {
            	taxCollectionRealTimeSearchTable = new TaxCollectionRealTimeSearchTable();
            	taxCollectionRealTimeSearchTable.setXm(rs.getString("xm"));
            	taxCollectionRealTimeSearchTable.setA1(rs.getBigDecimal("a1"));
            	taxCollectionRealTimeSearchTable.setA2(rs.getInt("a2"));
            	
            	tableList.add(taxCollectionRealTimeSearchTable);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return tableList;
	}

}
