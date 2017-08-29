package com.amx.bizservice.enums;

/**
 * 广告调用标识
 * @author DangerousHai
 *
 */
public enum AdvertisementUdidEnum {
	
	INDEX_BANNER(null,"index.baner"),INDEX_SLOGAN(null,"index.slogan"),INDEX_THEME(null,"index.theme"),
	INDEX_SCENE(null,"index.scene"),INDEX_REGION(null,"index.region"),INDEX_ARTICLE(null,"index.article"),
	INDEX_LINE(null,"index.line"),
	PRODUCT_BANNER(null,"product.banner"), PRODUCT_REGION(null,"product.region"),
	//INDEX_REGION_PRODUCT(null,"index.region-product"),INDEX_SCENE_PRODUCT(null,"index.scene-product"),	INDEX_LINE_CUSTOM(null,"index.line-custom")
	DEFAULT_BANNER(null,"default.banner"),DEFAULT_HOT_REGION(null,"default.hotRegion"),DEFAULT_SIFT_REGION(null,"default.siftRegion"),NAVBAR_HOT_WORD(null,"navbar.hotWord")
	;
	
	
	private String name;
	private String udid;
	
	private AdvertisementUdidEnum(String name, String id){
		this.name = name;
		this.udid = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUdid() {
		return udid;
	}

	public void setUdid(String udid) {
		this.udid = udid;
	}

}