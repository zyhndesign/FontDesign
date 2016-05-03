package com.cidic.fontdesign.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="chinese_character_tag")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ChineseCharacterTag implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},fetch = FetchType.LAZY)
	private ChineseCharacter chineseCharacter;

	@OneToOne
	private Tag tag;

	public ChineseCharacter getChineseCharacter() {
		return chineseCharacter;
	}

	public void setChineseCharacter(ChineseCharacter chineseCharacter) {
		this.chineseCharacter = chineseCharacter;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
}
