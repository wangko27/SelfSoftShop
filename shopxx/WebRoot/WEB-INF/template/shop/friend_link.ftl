<div id="friendLink" class="friendLink">
	<div class="pictureFriendLink">
		<div class="left"></div>
		<div class="middle">
			<ul>
				<@friend_link_list type="picture" count=10; friendLinkList>
					<#list friendLinkList as friendLink>
						<li>
							<a href="${friendLink.url}" title="${friendLink.name}" target="_blank">
								<img src="${base}${friendLink.logoPath}">
							</a>
						</li>
					</#list>
				</@friend_link_list>
			</ul>
		</div>
		<div class="right"></div>
	</div>
	<div class="textFriendLink">
		<div class="left"></div>
		<div class="middle">
			<ul>
				<@friend_link_list type="text" count=10; friendLinkList>
					<#list friendLinkList! as friendLink>
						<li>
							<a href="${friendLink.url}" title="${friendLink.name}" target="_blank">${friendLink.name}</a>
						</li>
					</#list>
				</@friend_link_list>
			</ul>
		</div>
		<div class="right"></div>
	</div>
</div>