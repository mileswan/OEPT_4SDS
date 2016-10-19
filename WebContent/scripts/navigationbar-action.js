/**
 * Author: mwan
 * Version: 1.0
 * Date: 2015/08/19
 * Description: logic to display navigation bar.
 */
var navigationBar = function(){
    
	return {
	
		activeHomeMenu: function(){
			
		    $('li.home-menu').addClass("active open");
		},
		
        activeMediaMenu: function(){
			
        	$('li.media-menu').addClass("active open");
		},
		
        activeNetworkMenu: function(){
			
        	$('li.network-menu').addClass("active open");
		},
		
        activeUserMenu: function(){
			
        	$('li.user-menu').addClass("active open");
		},
		
        activeSystemMenu: function(){
			
        	$('li.system-menu').addClass("active open");
		},
		
        activeHelpMenu: function(){
			
        	$('li.help-menu').addClass("active open");
		}
		
	};

}();