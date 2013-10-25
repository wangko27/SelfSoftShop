package net.shopxx.bean;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Bean类 - 商品图片
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXD7D506B0E8767709FDAC4269652F8E43
 * ============================================================================
 */

@JsonIgnoreProperties(value = {"sourceImagePath", "bigImagePath", "smallImagePath", "thumbnailImagePath"})
public class GoodsImage implements Comparable<GoodsImage> {
	
	public static final String IMAGE_PATH_SEPARATOR = "/";// 图片路径分隔符
	public static final String DESTINATION_IMAGE_FORMAT_NAME = "jpg";// 目标图片格式名称
	public static final String SOURCE_IMAGE_FOLDER_NAME = "source";// 图片（原）文件保存目录
	public static final String BIG_IMAGE_FOLDER_NAME = "big";// 图片（大）文件保存目录
	public static final String SMALL_IMAGE_FOLDER_NAME = "small";// 图片（小）文件保存目录
	public static final String THUMBNAIL_IMAGE_FOLDER_NAME = "thumbnail";// 图文（缩略）保存目录
	
	private String id;// ID
	private String path;// 路径
	private String sourceImageFormatName;// 图片（原）格式
	private String description;// 描述
	private Integer orderList;// 排序
	
	// 获得商品图片（原）路径
	public String getSourceImagePath() {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(path);
		stringBuffer.append(IMAGE_PATH_SEPARATOR);
		stringBuffer.append(SOURCE_IMAGE_FOLDER_NAME);
		stringBuffer.append(IMAGE_PATH_SEPARATOR);
		stringBuffer.append(id);
		stringBuffer.append(".");
		stringBuffer.append(sourceImageFormatName);
		return stringBuffer.toString();
	}

	// 获得商品图片（大）路径
	public String getBigImagePath() {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(path);
		stringBuffer.append(IMAGE_PATH_SEPARATOR);
		stringBuffer.append(BIG_IMAGE_FOLDER_NAME);
		stringBuffer.append(IMAGE_PATH_SEPARATOR);
		stringBuffer.append(id);
		stringBuffer.append(".");
		stringBuffer.append(DESTINATION_IMAGE_FORMAT_NAME);
		return stringBuffer.toString();
	}
	
	// 获得商品图片（小）路径
	public String getSmallImagePath() {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(path);
		stringBuffer.append(IMAGE_PATH_SEPARATOR);
		stringBuffer.append(SMALL_IMAGE_FOLDER_NAME);
		stringBuffer.append(IMAGE_PATH_SEPARATOR);
		stringBuffer.append(id);
		stringBuffer.append(".");
		stringBuffer.append(DESTINATION_IMAGE_FORMAT_NAME);
		return stringBuffer.toString();
	}
	
	// 获得商品图片（缩略）路径
	public String getThumbnailImagePath() {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(path);
		stringBuffer.append(IMAGE_PATH_SEPARATOR);
		stringBuffer.append(THUMBNAIL_IMAGE_FOLDER_NAME);
		stringBuffer.append(IMAGE_PATH_SEPARATOR);
		stringBuffer.append(id);
		stringBuffer.append(".");
		stringBuffer.append(DESTINATION_IMAGE_FORMAT_NAME);
		return stringBuffer.toString();
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == null) {
			return false;
		}
		if (object instanceof GoodsImage) {
			GoodsImage goodsImage = (GoodsImage) object;
			if (this.getId() == null || goodsImage.getId() == null) {
				return false;
			} else {
				return (this.getId().equals(goodsImage.getId()));
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return id == null ? System.identityHashCode(this) : (this.getClass().getName() + this.getId()).hashCode();
	}

	public int compareTo(GoodsImage goodsImage) {
		if (goodsImage.getOrderList() == null) {
			return 1;
		}
		if (this.getOrderList() == null) {
			return -1;
		}
		return this.getOrderList().compareTo(goodsImage.getOrderList());
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getSourceImageFormatName() {
		return sourceImageFormatName;
	}

	public void setSourceImageFormatName(String sourceImageFormatName) {
		this.sourceImageFormatName = sourceImageFormatName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getOrderList() {
		return orderList;
	}

	public void setOrderList(Integer orderList) {
		this.orderList = orderList;
	}

}