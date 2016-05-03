package com.cidic.fontdesign.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="chinese_character")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ChineseCharacter implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(name="author")
	private String author;
	
	@Column(name="title") 
	private String title;
	
	@Column(name="abstract") 
	private String abstract_;
	
	@Column(name="create_time") 
	private Date createTime;
	
	@Column(name="thumbnail") 
	private String thumbnail;
	
	@Column(name="top_tag") 
	private int topTag;
	
	@Column(name="category")
	private int category;
	
	@Column(name="content")
	private String content;

	@OneToMany(mappedBy = "chineseCharacter",cascade=CascadeType.ALL,fetch = FetchType.EAGER)
	private List<ChineseCharacterTag> chineseCharacterTag;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAbstract_() {
		return abstract_;
	}

	public void setAbstract_(String abstract_) {
		this.abstract_ = abstract_;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public int getTopTag() {
		return topTag;
	}

	public void setTopTag(int topTag) {
		this.topTag = topTag;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<ChineseCharacterTag> getChineseCharacterTag() {
		return chineseCharacterTag;
	}

	public void setChineseCharacterTag(List<ChineseCharacterTag> chineseCharacterTag) {
		this.chineseCharacterTag = chineseCharacterTag;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
}
