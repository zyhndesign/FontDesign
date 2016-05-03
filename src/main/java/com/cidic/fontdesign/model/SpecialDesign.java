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
@Table(name="special_design")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SpecialDesign implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(name="title")
	private String title;
	
	@Column(name="abstract")
	private String abstract_;
	
	@Column(name="teacher")
	private String teacher;
	
	@Column(name="create_time")
	private Date createTime;
	
	@Column(name="special_detail_id")
	private int specialDetailId;
	
	@Column(name="top_tag")
	private int topTag;
	
	@Column(name="bg")
	private String bg;
	
	@Column(name="thumbnail")
	private String thumbnail;
	
	@OneToMany(mappedBy = "specialDesign",cascade=CascadeType.ALL,fetch = FetchType.EAGER)
	private List<SpecialDesignTag> specialDesignTag;

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

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getSpecialDetailId() {
		return specialDetailId;
	}

	public void setSpecialDetailId(int specialDetailId) {
		this.specialDetailId = specialDetailId;
	}

	public int getTopTag() {
		return topTag;
	}

	public void setTopTag(int topTag) {
		this.topTag = topTag;
	}

	public String getBg() {
		return bg;
	}

	public void setBg(String bg) {
		this.bg = bg;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public List<SpecialDesignTag> getSpecialDesignTag() {
		return specialDesignTag;
	}

	public void setSpecialDesignTag(List<SpecialDesignTag> specialDesignTag) {
		this.specialDesignTag = specialDesignTag;
	}
	
}
