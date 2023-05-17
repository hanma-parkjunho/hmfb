$(document).ready(function(){
	hibs.init();
});


var hibs={
	init:function(){
		hibs.tabPanel.init();
		hibs.leftMenuInit();
		hibs.allMenuInit();
	},

    leftMenuInit:function(){
        $(document).on('click','.navi-group button',function(){
            if(!$(this).hasClass('on')){
                $(this).closest('.navi-group').find('button').removeClass('on');
                $(this).closest('.navi-group').find('>ul>li').removeClass('on');
                $(this).addClass('on');
                $(this).closest('li').addClass('on');
            }
        });
    },

    allMenuInit:function(){
        $(document).on('click', '.button-allmenu',function(){
            hibs.popup.open($(this),'#allMenuPopup');
        });
    },

    // POPUP
	popup:{
		open:function(clickEl,popupID){
			$('body').append($(popupID));
			$('#wrapper').attr('aria-hidden','true'); // 컨텐츠 영역에 포커스 안가도록 함(접근성)
			$('body').css('overflow','hidden');
			
			$(popupID).addClass('active');
			$(popupID).find('.popup-content').focus().removeAttr('tabindex');

			$(popupID).find('.btn-close').off('click').on('click',function(){
				hibs.popup.close(clickEl,popupID);
				
			});
			$(popupID).find('.close').off('click').on('click',function(){
				$(popupID).find('.btn-close').trigger('click');
			});
		},
		close:function(clickEl,popupID){
			$(popupID).removeClass('active');
			$(popupID).find('.popup-content').attr('tabindex','-1');
			
			if($('.popup-group.active').length==0){
				$('body').removeAttr('style'); // 스크롤 가능
				$('#wrapper').attr('aria-hidden','false'); // 컨텐츠 영역에 포커스 가도록 함(접근성)
			}
			if(clickEl=='') {
				$('body').focus();
			} else {
				$(clickEl).focus();
			}
		}
	},

	tabPanel:{
		init:function(){
			if($('.tabpanel-group').length){
				var tabPanelGroup,tabListGroup,tabPanelGroup;
				$(document).on('click','.tabpanel-group .tablist li',function(){
					tabPanelGroup=$(this).closest('.tabpanel-group');
					tabListGroup=$(this).closest('.tablist');
					tabPanelGroup=tabPanelGroup.find('.tab-body');
					var idx=$(this).index();
					tabListGroup.find('.tab').attr('aria-selected','false');
					tabListGroup.find('.tab').eq(idx).attr('aria-selected','true');
					tabPanelGroup.find('.tabpanel').attr('aria-expanded','false');
					tabPanelGroup.find('.tabpanel').eq(idx).attr('aria-expanded','true');
				});
			}
		}
	},
};