package net.shopxx.service;

import java.util.List;

import net.shopxx.bean.Pager;
import net.shopxx.entity.Comment;
import net.shopxx.entity.Goods;

/**
 * Service接口 - 评论
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX0173E6044D90E14E7C66F5208467F4F3
 * ============================================================================
 */

public interface CommentService extends BaseService<Comment, String> {
	
	/**
	 * 根据Pager获取评论分页对象（包含isShow = true的对象,不包含回复）
	 *            
	 * @param pager
	 *            Pager对象
	 *            
	 * @return 评论分页对象
	 */
	public Pager getCommentPager(Pager pager);
	
	/**
	 * 根据商品、pager获取评论集合,若无评论则返回null（只包含isShow = true的对象,不包含回复）
	 * 
	 * @param goods
	 *            商品
	 * 
	 * @param pager
	 *            pger
	 * 
	 * @return 评论集合
	 * 
	 */
	public Pager getCommentPager(Pager pager, Goods goods);
	
	/**
	 * 根据商品、最大结果数获取评论集合,若无评论则返回null（只包含isShow = true的对象）
	 * 
	 * @param goods
	 *            商品,null表示无限制
	 * 
	 * @param maxResults
	 *            最大结果数,null表示无限制
	 * 
	 * @return 评论集合
	 * 
	 */
	public List<Comment> getCommentList(Goods goods, Integer maxResults);
	
}