package com.amarsoft.bis.server.esb.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

/**
 * �ַ���ѹ������ѹ��
 * @author EX_WLJR_GAODEPENG
 *
 */
public class ZipUtils {
	
	/**
	 * ѹ����ԭ�ַ���--->ZipOutPutStreanѹ��-->תBASE64�ַ���
	 * ��ѹ��ѹ�����ַ���--->BASE64ת��--->ZipInPutStream��ѹ��
	 */

	private static final String DEFAULT_ENTITY_NAME = "0";
	/**
	 * ��ȡѹ�����ַ���
	 * @param source ԭʼ�ַ���
	 * @param entryName 
	 * @return
	 */
	public static String getCompressStr(String source){
		String base64EncodeStr = new String(Base64.encodeBase64(compress(source)));
		return base64EncodeStr;
	}
	/**
	 * ָ���ַ�����ȡѹ�����ַ���
	 * @param source ԭʼ�ַ���
	 * @param entryName 
	 * @return
	 */
	public static String getCompressStr(String source,String charset){
		String base64EncodeStr = new String(Base64.encodeBase64(compress(source,charset)));
		return base64EncodeStr;
	}
	
	/**
	 * ��ȡ��ѹ������
	 * @param encodeStr ѹ�����ַ���
	 * @return
	 */
	public static String getDecompressStr(String encodeStr){
		String source = decompress(Base64.decodeBase64(encodeStr.getBytes()));
		return source;
	}
	
	/**
	 * ָ���ַ�����ȡ��ѹ������
	 * @param encodeStr ѹ�����ַ���
	 * @return
	 */
	public static String getDecompressStr(String encodeStr,String charset){
		String source = decompress(Base64.decodeBase64(encodeStr.getBytes()),charset);
		return source;
	}
	
	/**
	 * �ַ���ѹ��
	 * @param source
	 * @param entryName
	 * @return
	 */
	public static final byte[] compress(String source) {
		if (StringUtils.isBlank(source))
			return null;
		byte[] compressed;
		ByteArrayOutputStream out = null;
		ZipOutputStream zout = null;

		try {
			out = new ByteArrayOutputStream();
			zout = new ZipOutputStream(out);
			ZipEntry entry = new ZipEntry(DEFAULT_ENTITY_NAME);//ZIP file entry,���붨��ZipEntry
			zout.putNextEntry(entry);
			zout.write(source.getBytes());
			zout.closeEntry();
			compressed = out.toByteArray();//����apiע�ͣ��˴�outΪʵ�������
		} catch (IOException e) {
			e.printStackTrace();
			compressed = null;
		} finally {
			if (zout != null) {
				try {
					zout.close();
				} catch (IOException e) {
				}
			}
		}
		if (out != null) {
			try {
				out.close();
			} catch (IOException e) {
			}
		}
		return compressed;
	}
	
	/**
	 * ָ���ַ���ѹ��
	 * @param source
	 * @param charset
	 * @return
	 */
	public static final byte[] compress(String source,String charset) {
		if (StringUtils.isBlank(source))
			return null;
		byte[] compressed;
		ByteArrayOutputStream out = null;
		ZipOutputStream zout = null;

		try {
			out = new ByteArrayOutputStream();
			zout = new ZipOutputStream(out);
			ZipEntry entry = new ZipEntry(DEFAULT_ENTITY_NAME);//ZIP file entry,���붨��ZipEntry
			zout.putNextEntry(entry);
			zout.write(source.getBytes(charset));
			zout.closeEntry();
			compressed = out.toByteArray();//����apiע�ͣ��˴�outΪʵ�������
		} catch (IOException e) {
			e.printStackTrace();
			compressed = null;
		} finally {
			if (zout != null) {
				try {
					zout.close();
				} catch (IOException e) {
				}
			}
		}
		if (out != null) {
			try {
				out.close();
			} catch (IOException e) {
			}
		}
		return compressed;
	}
	
	/**
	 * ��ѹ��
	 * @param compressed
	 * @return
	 */
	public static final String decompress(byte[] compressed) {
		if (compressed == null)
			return null;

		ByteArrayOutputStream out = null;
		ByteArrayInputStream in = null;
		ZipInputStream zin = null;
		String decompressed;
		try {
			out = new ByteArrayOutputStream();
			in = new ByteArrayInputStream(compressed);
			zin = new ZipInputStream(in);
			ZipEntry entry = zin.getNextEntry();//Reads the next ZIP file entry and positions the stream at the beginning of the entry data.
			byte[] buffer = new byte[1024];
			int offset = -1;
			while ((offset = zin.read(buffer,0,buffer.length)) != -1) {
				out.write(buffer, 0, offset);
			}
			decompressed = out.toString();
		} catch (IOException e) {
			e.printStackTrace();
			decompressed = null;
		} finally {
			if (zin != null) {
				try {
					zin.close();
				} catch (IOException e) {
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
		return decompressed;
	}
	
	/**
	 * ָ���ַ�����ѹ��
	 * @param compressed
	 * @param charset
	 * @return
	 */
	public static final String decompress(byte[] compressed,String charset) {
		if (compressed == null)
			return null;

		ByteArrayOutputStream out = null;
		ByteArrayInputStream in = null;
		ZipInputStream zin = null;
		String decompressed;
		try {
			out = new ByteArrayOutputStream();
			in = new ByteArrayInputStream(compressed);
			zin = new ZipInputStream(in);
			ZipEntry entry = zin.getNextEntry();//Reads the next ZIP file entry and positions the stream at the beginning of the entry data.
			byte[] buffer = new byte[1024];
			int offset = -1;
			while ((offset = zin.read(buffer,0,buffer.length)) != -1) {
				out.write(buffer, 0, offset);
			}
			decompressed = out.toString(charset);
		} catch (IOException e) {
			e.printStackTrace();
			decompressed = null;
		} finally {
			if (zin != null) {
				try {
					zin.close();
				} catch (IOException e) {
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
		return decompressed;
	}
	
	
	
	public static void main(String[] args) {
		String source = "1���������10	�ſ�100	��ծ�ʲ������ڱ���101	��ծ�ʲ�����������110	�������120	���������ֹ�����130	����Ҫ�ر��140	����������141	��������ҵ��142	���������ҵ��143	����������	�����˻��ʽ��ϻ�150	ί����ί��160	����ת��170	�ſ��20	�ֹ�����21	��ǰ����22	�����ۿ�23	������ǰ����24	������ǰ����25	����ͨ�浥����30	��Ϣ��������40	��������50	��ſ�51	�廹��52	�����ſ�53	��������55	�˻�ȫ����ȡ������56	�˻�ȫ���˻�������57	�˻�����ȡδ�ղ���������60	��֤��ۿ�70	������ת75	�����Ϣ80	����ת����81	����ת����90	������廹��95	�������ջ�96	��ʷ���������ֹ��ջ�97	�����������ջ�2���˻�������Ϣ*@paamogID*@paamsubjeco*@paamcuecy*@paamaccouype*@eu�õ��������˻���*@howsExcepio*12	FC-ICS�����ڲ��˻�*13	ICS-FC�����ڲ��˻�*90	½���쿪������������ר�ô���˻�*91	������ί�д���˻�*92	������ί�д�����Ϣ��*93	������ί�д��Ϣ��*94	������ί�д����ʽ����㡪�����˻�����71	���ʽ���ICS���ɻ�97	���������419�ʽ�����(���滧)140	������FC���ݹҿ�Ŀ150	������ICS���ݹҿ�Ŀ11	����-ICS�����ڲ��˻�12	FC-ICS�����ڲ��˻�10	����-FC�����ڲ��˻�13	ICS-FC�����ڲ��˻�20	���ҽ�����֧ȡ�˻�30	�ʽ����˻�40	��ѧ������Ϣ�˻�50	���б�ȫ��56	�����ʻ�60	���ÿ����������˻�55	С�������ʻ�70	ͨ��ͨ���˻�90	�����������ģ�ר�ô���˻�91	������ί�д���˻�92	������ί�д�����Ϣ��93	������ί�д��Ϣ��94	���������419�ʽ�����(���ջ�)95	������ί�д����ʽ����㡪���𣨿ͻ��ˣ�96	������ί�д����ʽ����㡪��Ϣ���ͻ��ˣ�100	���˴�����ʧ׼�����������110	�Թ�������ʧ׼�����������120	���˴�����ʧ׼���������ջ�130	�Թ�������ʧ׼���������ջ�80	FCӡ��˰�˻�81	ICSӡ��˰�˻����ױ��991001�ſ�992001����993001����994001��ſ�995001�廹��997001ί����ί��998001�ſ��";
		String encodeStr = getCompressStr(source);
		String decodeStr = getDecompressStr(encodeStr);
		System.out.println("ѹ����BASE64����"+encodeStr);
		System.out.println("BASE64��ѹ������"+decodeStr);
		System.out.println(decodeStr.equals(source));
		System.out.println("ѹ����Ϊ:"+encodeStr.length()+""+source.length());
	}
}
