/**
 * 新建下拉菜单js
 */

var timeout = 500;
var closetimer = 0;
var ddmenuitem = 0;
// 打开隐藏层
function mopen(id) {
	// 关闭计时器
	mcancelclosetime();

	// 关闭之前的层
	if (ddmenuitem)
		ddmenuitem.style.visibility = 'hidden';

	// 获得新的并显示层
	ddmenuitem = document.getElementById(id);
	ddmenuitem.style.visibility = 'visible';

}
// 关闭显示层
function mclose() {
	if (ddmenuitem)
		ddmenuitem.style.visibility = 'hidden';
}

// 关闭定时器
function mclosetime() {
	closetimer = window.setTimeout(mclose, timeout);
}

// 取消关闭定时器
function mcancelclosetime() {
	if (closetimer) {
		window.clearTimeout(closetimer);
		closetimer = null;
	}
}

// 点击关闭显示层
document.onclick = mclose;

//按钮触发变色
function mb_over(id){
	document.getElementById(id).style.backgroundColor = '#45b6af';
}
//按钮离开变色
function mb_out(id){
	document.getElementById(id).style.backgroundColor = '#6a9c91';
}
