package com.oept.autods.unit.test;

import java.io.IOException;
import java.util.Vector;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

public class SnmpUtil {

      private Snmp snmp = null;

      private Address targetAddress = null;

      public void initComm() throws IOException {
             
             // 设置Agent方的IP和端口
             targetAddress = GenericAddress.parse("udp:192.168.1.222/161");
             TransportMapping<?> transport = new DefaultUdpTransportMapping();
             snmp = new Snmp(transport);
             transport.listen();
      }

      @SuppressWarnings("unchecked")
	public void sendPDU() throws IOException {
    	  try {   
    	  // 设置 target
             CommunityTarget target = new CommunityTarget();
             target.setCommunity(new OctetString("public"));
             target.setAddress(targetAddress);
             // 通信不成功时的重试次数
             target.setRetries(2);
             // 超时时间
             target.setTimeout(1500);
             target.setVersion(SnmpConstants.version1);
             // 创建 PDU
             PDU pdu = new PDU();
             pdu.add(new VariableBinding(new OID(new int[] { 1, 3, 6, 1, 2, 1, 1, 5, 0 })));
             pdu.add(new VariableBinding(new OID(new int[] { 1, 3, 6, 1, 2, 1, 1, 4, 0 })));
             pdu.add(new VariableBinding(new OID(new int[] { 1, 3, 6, 1, 2, 1, 1, 6, 0 })));
             pdu.add(new VariableBinding(new OID(new int[] { 1, 3, 6, 1, 2, 1, 1, 1, 0 })));
             pdu.add(new VariableBinding(new OID(new int[] { 1, 3, 6, 1, 2, 1, 1, 2, 0 })));
             pdu.add(new VariableBinding(new OID(new int[] { 1, 3, 6, 1, 2, 1, 1, 3, 0 })));
             pdu.add(new VariableBinding(new OID(new int[] { 1, 3, 6, 1, 2, 1, 1, 7, 0 })));
             // MIB的访问方式
             pdu.setType(PDU.GET);
             // 向Agent发送PDU，并接收Response
             ResponseEvent respEvnt = snmp.send(pdu, target);
             // 解析Response
             if (respEvnt != null && respEvnt.getResponse() != null) {
                    Vector<VariableBinding> recVBs = (Vector<VariableBinding>) respEvnt.getResponse().getVariableBindings();
                    for (int i = 0; i < recVBs.size(); i++) {
                           VariableBinding recVB = recVBs.elementAt(i);
                           System.out.println(recVB.getOid() + " : " + recVB.getVariable());
                    }
             }
    	  }catch(IOException e){
    		  throw e;
    	  }
      }
}
