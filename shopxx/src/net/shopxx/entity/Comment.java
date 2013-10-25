package net.shopxx.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import org.hibernate.annotations.ForeignKey;

/**
 * 实体类 - 评论
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX98FCE833E4347550503D1E5C947BB0B2
 * ============================================================================
 */

@Entity
public class Comment extends BaseEntity {

	private static final long serialVersionUID = 8413023474799399082L;
	
	public static final int DEFAULT_COMMENT_LIST_PAGE_SIZE = 12;// 商品评论默认每页显示数
	
	private String username;// 用户名
	private String content;// 内容
	private String contact;// 联系方式
	private String ip;// IP
	private Boolean isShow;// 是否显示
	private Boolean isAdminReply;// 是否为管理员回复
	
	private Goods goods;// 商品
	private Comment forComment;// 评论
	
	private Set<Comment> replyCommentSet = new HashSet<Comment>();// 回复
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Lob
	@Column(nullable = false)
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getContact() {
		return contact;
	}
	
	public void setContact(String contact) {
		this.contact = contact;
	}
	
	@Column(nullable = false)
	public String getIp() {
		return ip;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	@Column(nullable = false)
	public Boolean getIsShow() {
		return isShow;
	}
	
	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}
	
	@Column(nullable = false)
	public Boolean getIsAdminReply() {
		return isAdminReply;
	}
	
	public void setIsAdminReply(Boolean isAdminReply) {
		this.isAdminReply = isAdminReply;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, updatable = false)
	@ForeignKey(name = "fk_comment_goods")
	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@ForeignKey(name = "fk_comment_for_comment")
	public Comment getForComment() {
		return forComment;
	}
	
	public void setForComment(Comment forComment) {
		this.forComment = forComment;
	}

	@OneToMany(mappedBy = "forComment", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	@OrderBy("createDate asc")
	public Set<Comment> getReplyCommentSet() {
		return replyCommentSet;
	}
	
	public void setReplyCommentSet(Set<Comment> replyCommentSet) {
		this.replyCommentSet = replyCommentSet;
	}
	
	// 保存处理
	@Override
	@Transient
	public void onSave() {
		if (isShow == null) {
			isShow = false;
		}
		if (isAdminReply == null) {
			isAdminReply = false;
		}
	}
	
	// 更新处理
	@Override
	@Transient
	public void onUpdate() {
		if (isShow == null) {
			isShow = false;
		}
		if (isAdminReply == null) {
			isAdminReply = false;
		}
	}

}