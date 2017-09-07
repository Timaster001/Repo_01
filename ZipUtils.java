

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
	
	
	

}
