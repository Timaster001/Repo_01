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
 * 字符串压缩、解压缩
 * @author EX_WLJR_GAODEPENG
 *
 */
public class ZipUtils {
	
	/**
	 * 压缩：原字符串--->ZipOutPutStrean压缩-->转BASE64字符串
	 * 解压：压缩后字符串--->BASE64转码--->ZipInPutStream解压缩
	 */

	private static final String DEFAULT_ENTITY_NAME = "0";
	/**
	 * 获取压缩后字符串
	 * @param source 原始字符串
	 * @param entryName 
	 * @return
	 */
	public static String getCompressStr(String source){
		String base64EncodeStr = new String(Base64.encodeBase64(compress(source)));
		return base64EncodeStr;
	}
	/**
	 * 指定字符集获取压缩后字符串
	 * @param source 原始字符串
	 * @param entryName 
	 * @return
	 */
	public static String getCompressStr(String source,String charset){
		String base64EncodeStr = new String(Base64.encodeBase64(compress(source,charset)));
		return base64EncodeStr;
	}
	
	/**
	 * 获取解压后数据
	 * @param encodeStr 压缩后字符串
	 * @return
	 */
	public static String getDecompressStr(String encodeStr){
		String source = decompress(Base64.decodeBase64(encodeStr.getBytes()));
		return source;
	}
	
	/**
	 * 指定字符集获取解压后数据
	 * @param encodeStr 压缩后字符串
	 * @return
	 */
	public static String getDecompressStr(String encodeStr,String charset){
		String source = decompress(Base64.decodeBase64(encodeStr.getBytes()),charset);
		return source;
	}
	
	/**
	 * 字符串压缩
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
			ZipEntry entry = new ZipEntry(DEFAULT_ENTITY_NAME);//ZIP file entry,必须定义ZipEntry
			zout.putNextEntry(entry);
			zout.write(source.getBytes());
			zout.closeEntry();
			compressed = out.toByteArray();//根据api注释，此处out为实际输出流
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
	 * 指定字符集压缩
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
			ZipEntry entry = new ZipEntry(DEFAULT_ENTITY_NAME);//ZIP file entry,必须定义ZipEntry
			zout.putNextEntry(entry);
			zout.write(source.getBytes(charset));
			zout.closeEntry();
			compressed = out.toByteArray();//根据api注释，此处out为实际输出流
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
	 * 解压缩
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
	 * 指定字符集解压缩
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
		String source = "1、单类据型10	放款100	抵债资产抵逾期本金101	抵债资产抵正常本金110	表外结清120	核销贷款手工结清130	账务要素变更140	公积金清算141	公积金借记业务142	公积金贷记业务143	昆明公积金	二级账户资金上划150	委存销委贷160	贷款转让170	放款补帐20	手工还款21	提前还款22	批量扣款23	批量提前还款24	网银提前还款25	金领通存单还款30	利息复利增减40	调整本金50	冲放款51	冲还款52	撤销放款53	撤销还款55	退货全额收取手续费56	退货全额退回手续费57	退货不收取未收部分手续费60	保证金扣款70	贷款内转75	贷款结息80	正常转逾期81	逾期转正常90	公积金冲还贷95	核销后收回96	历史核销贷款手工收回97	大额核销贷款收回2、账户类型信息*@paamogID*@paamsubjeco*@paamcuecy*@paamaccouype*@eu得到公积金账户：*@howsExcepio*12	FC-ICS往来内部账户*13	ICS-FC往来内部账户*90	陆家嘴开立“个贷基金”专用存款账户*91	公积金委托存款账户*92	公积金委托贷款利息户*93	公积金委托贷款罚息户*94	公积金委托贷款资金清算—本金账户类型71	新资金监管ICS过渡户97	公积金贷款419资金清算(代垫户)140	个贷在FC中暂挂科目150	个贷在ICS中暂挂科目11	零售-ICS往来内部账户12	FC-ICS往来内部账户10	零售-FC往来内部账户13	ICS-FC往来内部账户20	按揭金收益支取账户30	资金监管账户40	助学贷款贴息账户50	银行保全户56	理赔帐户60	信用卡清算往来账户55	小消保费帐户70	通存通兑账户90	公积金贷款（中心）专用存款账户91	公积金委托存款账户92	公积金委托贷款利息户93	公积金委托贷款罚息户94	公积金贷款419资金清算(代收户)95	公积金委托贷款资金清算—本金（客户账）96	公积金委托贷款资金清算—利息（客户账）100	个人贷款损失准备—当年核销110	对公贷款损失准备—当年核销120	个人贷款损失准备—当年收回130	对公贷款损失准备—当年收回80	FC印花税账户81	ICS印花税账户交易编号991001放款992001还款993001清算994001冲放款995001冲还款997001委存销委贷998001放款补账";
		String encodeStr = getCompressStr(source);
		String decodeStr = getDecompressStr(encodeStr);
		System.out.println("压缩后BASE64串："+encodeStr);
		System.out.println("BASE64解压缩串："+decodeStr);
		System.out.println(decodeStr.equals(source));
		System.out.println("压缩率为:"+encodeStr.length()+""+source.length());
	}
}
