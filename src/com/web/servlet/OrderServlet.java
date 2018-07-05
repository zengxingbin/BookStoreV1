package com.web.servlet;

import static com.util.FormatUtils.formatString;

import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.domain.Book;
import com.domain.Order;
import com.domain.OrderItem;
import com.domain.User;
import com.domain.YeePayRequestParameter;
import com.execption.OrderException;
import com.service.OrderService;
import com.service.factory.OrderServiceFactory;
import com.service.impl.OrderServiceImpl;
import com.yeepay.Configuration;
import com.yeepay.PaymentForOnlineService;

public class OrderServlet extends BaseServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 5675656935984242455L;

    /**
     * Constructor of the object.
     */
    public OrderServlet() {
        super();
    }
    
    @SuppressWarnings("unchecked")
    public void createOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> paramMap = request.getParameterMap();
        Order order = new Order();
        try {
            BeanUtils.populate(order, paramMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        Map<Book, Integer> cart = (Map<Book, Integer>) request.getSession().getAttribute("cart");
        float money = 0.0f;
        ArrayList<OrderItem> orderItems = new ArrayList<OrderItem>();
        for (Entry<Book, Integer> entry : cart.entrySet()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setBook(entry.getKey());
            orderItem.setBuynum(entry.getValue());
            orderItem.setOrder(order);
            money += entry.getKey().getPrice() * entry.getValue();
            orderItems.add(orderItem);
        }
        
        order.setMoney(money);
        order.setOrderItems(orderItems);
        order.setUser((User) request.getSession().getAttribute("loginUser"));
        order.setId(UUID.randomUUID().toString());
        
        // 调用业务逻辑
        OrderService orderService = new OrderServiceFactory().createOrderService();
        try {
            orderService.addOrder(order);
            cart.clear();
            request.getRequestDispatcher("/createOrderSuccess.jsp").forward(request, response);
        } catch (OrderException e) {
            e.printStackTrace();
        }
    
    }

    public void orderList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        OrderService orderService = new OrderServiceImpl();
        try {
            List<Order> orders = orderService.findOrdersByUserId(loginUser.getId());
            request.setAttribute("orders", orders);
            request.getRequestDispatcher("/orderlist.jsp").forward(request, response);
            return;
        } catch (OrderException e) {
            e.printStackTrace();
            return;
        }
        
    }

    public void orderInformation(HttpServletRequest request, HttpServletResponse response) {
        String orderId = request.getParameter("orderId");
        if(orderId != null) {
            OrderService orderService = new OrderServiceImpl();
            try {
                Order order = orderService.findOrderByOrderId(orderId);
                if(order != null) {
                    request.setAttribute("order", order);
                    request.getRequestDispatcher("/orderInfo.jsp").forward(request, response);
                } else { // 订单已失效
                    
                }
                
                return;
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
    }

    public void deleteOrder(HttpServletRequest request, HttpServletResponse response) {
        String orderId = request.getParameter("orderId");
        if(orderId != null) {
            OrderService orderService = new OrderServiceFactory().createOrderService();
            try {
                orderService.deleteOrder(orderId);
            } catch (OrderException e) {
                e.printStackTrace();
            }
        }
    }

    public void payOrder(HttpServletRequest request, HttpServletResponse response) {
        String orderId = request.getParameter("orderId");
        if(orderId != null) {
            OrderService orderService = new OrderServiceImpl();
            try {
                Order order = orderService.findOrderByOrderId(orderId);
                if(order != null) {
                    if(order.getPaystate() == 0) {
                        request.setAttribute("order", order);
                        request.getRequestDispatcher("/pay.jsp").forward(request, response);
                    } else { // 订单已支付
                        
                    }
                    
                } else { // 订单已失效
                    
                }
                
                return;
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
    }

    public void confirmPayment(HttpServletRequest request, HttpServletResponse response) {
        String orderId = request.getParameter("orderId");
        if(orderId != null) {
            OrderService orderService = new OrderServiceImpl();
            try {
                Order order = orderService.findOrderByOrderId(orderId);
                if(order != null) {
                    if(order.getPaystate() == 0) { // 订单未付款
                        String keyValue                 = formatString(Configuration.getInstance().getValue("keyValue"));           // 商家密钥
                        String nodeAuthorizationURL     = formatString(Configuration.getInstance().getValue("yeepayCommonReqURL")); // 交易请求地址
                        // 商家设置用户购买商品的支付信息
                        String    p0_Cmd                = formatString("Buy");                                                      // 在线支付请求，固定值 ”Buy”
                        String    p1_MerId              = formatString(Configuration.getInstance().getValue("p1_MerId"));           // 商户编号
                        String    p2_Order              = formatString(order.getId());                          // 商户订单号
                        String    p3_Amt                = formatString(order.getMoney() + "");                  // 支付金额
                        String    p4_Cur                = formatString("CNY");                                  // 交易币种
                        String    p5_Pid                = formatString("unknow");                               // 商品名称
                        String    p6_Pcat               = formatString("unknow");                               // 商品种类
                        String    p7_Pdesc              = formatString("unknow");                               // 商品描述
                        String    p8_Url                = formatString("http://localhost:8080/order?action=paySuccess");    // 商户接收支付成功数据的地址
                        String    p9_SAF                = formatString("1");                                    // 需要填写送货信息 0：不需要  1:需要
                        String    pa_MP                 = formatString("unknow");                               // 商户扩展信息
                        String    pd_FrpId              = formatString("pd_FrpId");                             // 银行编码
                        // 银行编号必须大写
                        pd_FrpId = pd_FrpId.toUpperCase();
                        String    pr_NeedResponse       = formatString("1");                                    // 是否需要应答机制
                        String    hmac                  = formatString("");                                                         // 交易签名串
                        
                        // 获得MD5-HMAC签名
                        hmac = PaymentForOnlineService.getReqMd5HmacForOnlinePayment(p0_Cmd,
                                p1_MerId,p2_Order,p3_Amt,p4_Cur,p5_Pid,p6_Pcat,p7_Pdesc,
                                p8_Url,p9_SAF,pa_MP,pd_FrpId,pr_NeedResponse,keyValue);
                        
                        YeePayRequestParameter yeePayReq = new YeePayRequestParameter(keyValue, nodeAuthorizationURL, p0_Cmd, 
                                                                                    p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, 
                                                                                    p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP, 
                                                                                    pd_FrpId, pr_NeedResponse, hmac);
                        System.out.println(yeePayReq);
                        request.setAttribute("yeePayReq", yeePayReq);
                        request.getRequestDispatcher("/reqpay.jsp").forward(request, response);     
                        
                    } else { // 订单已付款
                        
                    }
                    
                } else { // 订单已失效
                    
                }
                
                return;
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
    }

    public void paySuccess(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        String keyValue   = formatString(Configuration.getInstance().getValue("keyValue"));     // 商家密钥
        String r0_Cmd     = formatString(request.getParameter("r0_Cmd"));                       // 业务类型
        String p1_MerId   = formatString(Configuration.getInstance().getValue("p1_MerId"));     // 商户编号
        String r1_Code    = formatString(request.getParameter("r1_Code"));                      // 支付结果
        String r2_TrxId   = formatString(request.getParameter("r2_TrxId"));                     // 易宝支付交易流水号

        String r3_Amt     = formatString(request.getParameter("r3_Amt"));                       // 支付金额
        String r4_Cur     = formatString(request.getParameter("r4_Cur"));                       // 交易币种
        String r5_Pid     = formatString(request.getParameter("r5_Pid"));                       // 商品名称
        String r6_Order   = formatString(request.getParameter("r6_Order"));                     // 商户订单号

        String r7_Uid     = formatString(request.getParameter("r7_Uid"));                       // 易宝支付会员ID
        String r8_MP      = formatString(request.getParameter("r8_MP"));                        // 商户扩展信息
        String r9_BType   = formatString(request.getParameter("r9_BType"));                     // 交易结果返回类型
        String hmac       = formatString(request.getParameter("hmac"));                         // 签名数据
        boolean isOK = false;
        // 校验返回数据包

        isOK = PaymentForOnlineService.verifyCallback(hmac,p1_MerId,r0_Cmd,r1_Code, 
                r2_TrxId,r3_Amt,r4_Cur,r5_Pid,r6_Order,r7_Uid,r8_MP,r9_BType,keyValue);
        //PrintWriter out = response.getWriter();
        PrintStream out = System.out;
        if(isOK) {
            
            if(r1_Code.equals("1")) {
                // 产品通用接口支付成功返回-浏览器重定向
                if(r9_BType.equals("1")) {
                    out.println("callback方式:产品通用接口支付成功返回-浏览器重定向");
                    // 产品通用接口支付成功返回-服务器点对点通讯
                } else if(r9_BType.equals("2")) {
                    // 如果在发起交易请求时   设置使用应答机制时，必须应答以"success"开头的字符串，大小写不敏感
                    out.println("SUCCESS");
                  // 产品通用接口支付成功返回-电话支付返回        
                } else if(r9_BType.equals("3")) {
                    
                }
                // 下面页面输出是测试时观察结果使用
                out.println("<br>交易成功!<br>商家订单号:" + r6_Order + "<br>支付金额:" + r3_Amt + "<br>易宝支付交易流水号:" + r2_TrxId);
                
                OrderService orderService = new OrderServiceImpl();
                String orderId = r6_Order;
                int paystate = 1;
                try {
                    orderService.modifyOrderPaystate(orderId, paystate);
                    response.sendRedirect(request.getContextPath() + "/paysuccess.jsp");
                } catch (OrderException e) {
                    e.printStackTrace();
                }
            }
        } else {
            out.println("交易签名被篡改!");
        }
        
        out.flush();
        out.close();
    }
}
