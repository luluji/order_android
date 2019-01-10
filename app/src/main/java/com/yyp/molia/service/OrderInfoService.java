package com.yyp.molia.service;

import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.yyp.molia.domain.OrderInfo;
import com.yyp.molia.utils.HttpUtil;

/*预约管理业务逻辑层*/
public class OrderInfoService {
	/* 添加预约 */
	public String AddOrderInfo(OrderInfo orderInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("orderId", orderInfo.getOrderId() + "");
		params.put("uesrObj", orderInfo.getUesrObj());
		params.put("doctor", orderInfo.getDoctor());
		params.put("orderDate", orderInfo.getOrderDate().toString());
		params.put("timeSlotObj", orderInfo.getTimeSlotObj() + "");
		params.put("visiteStateObj", orderInfo.getVisiteStateObj() + "");
		params.put("introduce", orderInfo.getIntroduce());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "OrderInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
//	/* 查询预约 */
//	public List<OrderInfo> QueryOrderInfo(OrderInfo queryConditionOrderInfo) throws Exception {
//		String urlString = HttpUtil.BASE_URL + "OrderInfoServlet?action=query";
//		if(queryConditionOrderInfo != null) {
//			urlString += "&uesrObj=" + URLEncoder.encode(queryConditionOrderInfo.getUesrObj(), "UTF-8") + "";
//			urlString += "&doctor=" + URLEncoder.encode(queryConditionOrderInfo.getDoctor(), "UTF-8") + "";
//			if(queryConditionOrderInfo.getOrderDate() != null) {
//				urlString += "&orderDate=" + URLEncoder.encode(queryConditionOrderInfo.getOrderDate().toString(), "UTF-8");
//			}
//			urlString += "&timeSlotObj=" + queryConditionOrderInfo.getTimeSlotObj();
//			urlString += "&visiteStateObj=" + queryConditionOrderInfo.getVisiteStateObj();
//		}
//		URL url = new URL(urlString);
//		SAXParserFactory spf = SAXParserFactory.newInstance();
//		SAXParser sp = spf.newSAXParser();
//		XMLReader xr = sp.getXMLReader();
//
//		OrderInfoListHandler orderInfoListHander = new OrderInfoListHandler();
//		xr.setContentHandler(orderInfoListHander);
//		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
//		InputSource is = new InputSource(isr);
//		xr.parse(is);
//		List<OrderInfo> orderInfoList = orderInfoListHander.getOrderInfoList();
//		return orderInfoList;
//	}
	/* 更新预约 */
	public boolean UpdateOrderInfo(String doctorNo) {
		boolean result=false;
		try{
//			result = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "OrderInfo?"+doctorNo, null, "UTF-8");
//			if (re)
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/* 删除预约 */
	public String DeleteOrderInfo(int orderId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("orderId", orderId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "OrderInfo?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "预约信息删除失败!";
		}
	}
	/* 根据预约id获取预约对象 */
	public OrderInfo GetOrderInfo(int orderId)  {
		List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("orderId", orderId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "OrderInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				OrderInfo orderInfo = new OrderInfo();
				orderInfo.setOrderId(object.getInt("orderId"));
				orderInfo.setUesrObj(object.getString("uesrObj"));
				orderInfo.setDoctor(object.getString("doctor"));
				orderInfo.setOrderDate(Timestamp.valueOf(object.getString("orderDate")));
				orderInfo.setTimeSlotObj(object.getInt("timeSlotObj"));
				orderInfo.setVisiteStateObj(object.getInt("visiteStateObj"));
				orderInfo.setIntroduce(object.getString("introduce"));
				orderInfoList.add(orderInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = orderInfoList.size();
		if(size>0) return orderInfoList.get(0);
		else return null;
	}
}
