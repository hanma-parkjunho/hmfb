/**
 * jquery extend plagin
 */
 (function ($) {
  
  	$.contentType = {
	  	default : 'application/x-www-form-urlencoded; charset=UTF-8',
	  	json : 'application/json; charset=utf-8'
  	};	
	
 	$.submit = (form, pageIndex, pageUnit, pageSize) => {
	  	$(form).append($('<input>', {type: 'hidden', name: 'pageIndex', value: pageIndex}));
	  	$(form).append($('<input>', {type: 'hidden', name: 'pageUnit', value: pageUnit}));
	  	$(form).append($('<input>', {type: 'hidden', name: 'pageSize', value: pageSize}));
	  	$(form).append($('<input>', {type: 'hidden', name: 'selUprnSeqNo', value: document.naviForm.selUprnSeqNo.value}));
	  	$(form).append($('<input>', {type: 'hidden', name: 'selSeqNo', value: document.naviForm.selSeqNo.value}));
	  	form.submit();
  	};
	
  	$.api = (params, successCallback, errorCallback, hideProgress) => {
		$.loading(true, () => {
			let options = $.extend({
		      data: {},
		      method : 'post', 
		      contentType : $.contentType.json
		    }, params );
			console.log(options)
		    $.ajax({
				url : options.url,
		        data : options.contentType === $.contentType.default ? options.data : JSON.stringify(options.data),
		        contentType : options.contentType,
		        method : options.method,
		        success : function(res){
		            $.loading(false, () => {
			            if(typeof successCallback === 'function')
			            	successCallback.call(this, res);
		            });
		        },
		        error : function(xhr, status, error){
		            console.log(xhr);
		            $.loading(false, () => {
			            if(xhr.responseJSON != undefined && xhr.responseJSON.message != undefined) {
			            	$.alert(xhr.responseJSON.message, ()=>{
			            		if(xhr.status === 401) {
			            			location.href = '/'
			            		} else {
			            			if(typeof errorCallback === 'function')
			            				errorCallback.call(this, xhr.responseJSON);
			            		}
			            	});	
			            } else {
				    		$.alert('처리 중 오류가 발생 하였습니다.', ()=>{
				    			if(xhr.status === 401) {
			            			location.href = '/'
			            		} else {
				    				if(typeof errorCallback === 'function')
				    					errorCallback.call(this, xhr);
				    			}
				    		});
			            }
		            })
		        }
			});
		})
  	};
  
  	$.get = (params, successCallback, errorCallback, hideProgress) => {
  		$.api($.extend({contentType : $.contentType.default, method : 'get'}, params), successCallback, errorCallback, hideProgress)
  	};
  
  	$.form = (params, successCallback, errorCallback, hideProgress) => {
  		$.api($.extend({contentType : $.contentType.default, method : 'post'}, params), successCallback, errorCallback, hideProgress)
  	};
  
  	$.trim = (val) => {
  		return val.replace(/\s/g, '');
  	};
  
  	$.isEmpty = (val) => {
		return (typeof val === 'undefined' ||
		        val === 'undefined' ||
		        val === null ||
		        val === 'null' ||
		        (typeof val === 'object' && !Object.keys(val).length)) ||
		        (typeof val == 'string' && !$.trim(val).length) ? true : false;
  	};
  
  	$.nvl = (val, str) => {
  		return $.isEmpty(val) ? ($.isEmpty(str) ? "" : str) : val;
  	};
  
  	$.isPwdValid = (password) => {
  		return /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,12}$/.test(password);
  	};
  
  	$.popup = {
		open : (clickEl, popupID, callback) => {
			$('body').append($(popupID));
			$('#wrapper').attr('aria-hidden','true'); // 컨텐츠 영역에 포커스 안가도록 함(접근성)
			$('body').css('overflow','hidden');
			
			$(popupID).addClass('active');
			$(popupID).find('.popup-content').focus().removeAttr('tabindex');
		
			$(popupID).find('.btn-close').off('click').on('click',function(){
				$.popup.close(clickEl,popupID);
				
			});
			$(popupID).find('.dim').off('click').on('click',function(){
				$(popupID).find('.btn-close').trigger('click');
			});
			$(popupID).find('.close').off('click').on('click',function(){
				$(popupID).find('.btn-close').trigger('click');
			});
			if(typeof callback === 'function')
	        	callback.call(this, $(popupID));
		},
		close: (clickEl,popupID, callback) => {
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
			
			if(typeof callback === 'function')
	        	callback.call(this, $(popupID));
		}
  	};
  
 	$.alert = (altMsg, altCallback) => {
  
		let popupDiv = $('#alertPopup')
		
		$('body').append(popupDiv);	
		$('#wrapper').attr('aria-hidden','true');
		$('body').css('overflow','hidden');
		
		popupDiv.addClass('active');
		popupDiv.find('.popup-content').focus().removeAttr('tabindex');
		popupDiv.find('.popup-inner pre').text(altMsg);
		
		popupDiv.find('.close').off('click').on('click',function(){
			popupDiv.find('.btn-close').trigger('click');
		});
		
		popupDiv.find('.btn-close').off('click').on('click',function(){
			popupDiv.removeClass('active');
			popupDiv.find('.popup-content').attr('tabindex','-1');
			if($('.popup-group.active').length==0){
				$('body').removeAttr('style'); // 스크롤 가능
				$('#wrapper').attr('aria-hidden','false'); // 컨텐츠 영역에 포커스 가도록 함(접근성)
			}
			if(typeof altCallback === 'function')
				altCallback.call(this, {});
		});
	
  	};
  
	$.confirm = (cfmMsg, tCallback, fCallback) => {
  
		let popupDiv = $('#confirmPopup')
		
		$('body').append(popupDiv);	
		$('#wrapper').attr('aria-hidden','true');
		$('body').css('overflow','hidden');
		
		popupDiv.addClass('active');
		popupDiv.find('.popup-content').focus().removeAttr('tabindex');
		popupDiv.find('.popup-inner p').text(cfmMsg);
		
		popupDiv.find('.btn-group button').off('click').on('click',function(){
			popupDiv.find('.btn-close').trigger('click');
			if($(this).hasClass('check')){
				if(typeof tCallback === 'function')
					tCallback.call(this, {});
			} else {
				if(typeof fCallback === 'function')
					fCallback.call(this, {});
			}
		});
		
		popupDiv.find('.btn-close').off('click').on('click',function(){
			popupDiv.removeClass('active');
			popupDiv.find('.popup-content').attr('tabindex','-1');
			if($('.popup-group.active').length==0){
				$('body').removeAttr('style'); // 스크롤 가능
				$('#wrapper').attr('aria-hidden','false'); // 컨텐츠 영역에 포커스 가도록 함(접근성)
			}
		});
		
	
  	};
  	
  	$.loading = (isShow, tCallback) => {
  		
  		let popupDiv = $('#lodingPopup')
  		
  		if(isShow) {
  		
  			$('body').append(popupDiv);	
			$('#wrapper').attr('aria-hidden','true');
			$('body').css('overflow','hidden');
			popupDiv.addClass('active');
			popupDiv.find('.popup-content').focus().removeAttr('tabindex');
			if(typeof tCallback === 'function')
					tCallback.call(this, {});
					
  		} else {
  			
  			popupDiv.removeClass('active');
  			popupDiv.find('.popup-content').attr('tabindex','-1');
  			if($('.popup-group.active').length==0){
				$('body').removeAttr('style'); // 스크롤 가능
				$('#wrapper').attr('aria-hidden','false'); // 컨텐츠 영역에 포커스 가도록 함(접근성)
			}
			if(typeof tCallback === 'function')
					tCallback.call(this, {});
					
  		}
	
  	};
  
  	$.addDays = (date, days) => {
    	let d = new Date(date);
    	d.setDate(d.getDate() + days);
    	return toStringByFormatting(d, '.');
	};
	
	$.leftPad = (value) => {
	    if (value >= 10)
	        return value;
	    return `0${value}`;
	};

	$.toStringByFormatting = (source, delimiter = '-') => {
	    const year = source.getFullYear();
	    const month = leftPad(source.getMonth() + 1);
	    const day = leftPad(source.getDate());
	    return [year, month, day].join(delimiter);
	};
	
	$.getDateDiff = (d1, d2) => {
	  const date1 = new Date(d1);
	  const date2 = new Date(d2);
	  const diffDate = date1.getTime() - date2.getTime();
	  return Math.abs(diffDate / (1000 * 60 * 60 * 24)); // 밀리세컨 * 초 * 분 * 시 = 일
	};
  
}(jQuery));