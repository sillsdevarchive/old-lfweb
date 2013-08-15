jQuery(function() {
	initInputs();
	initCustomForms();
	initSlider();
	initOpenClose();
	VSA_initScrollbars();
});

function initUploader(elementId, fileAddCallback, doneCallback, failCallback) {
	jQuery(function() {
		'use strict';
		// Change this to the location of your server-side upload handler:
		var url = 'service/fileUploader/';
		jQuery('#' + elementId).fileupload({
			url : url,
			dataType : 'json',
			autoUpload : false,
			acceptFileTypes : /(\.|\/)(text|txt)$/i,
			maxFileSize : 1024 * 1024 * 5 // 5 MB
		}).on('fileuploadadd', function(e, data) {
			jQuery.each(data.files, function(index, file) {
				var callbackObj = new Object();
				callbackObj.value = file.name;
				callbackObj.success = true;
				callbackObj.data = data;
				fileAddCallback(callbackObj);
			});
		}).on('fileuploadprocessalways', function(e, data) {
			var index = data.index, file = data.files[index];
			if (file.error) {
				var callbackObj = new Object();
				callbackObj.value = file.name;
				callbackObj.success = false;
				callbackObj.error = file.error;
				failCallback(callbackObj);
			}
		}).on('fileuploadprogressall', function(e, data) {
			var progress = parseInt(data.loaded / data.total * 100, 10);
			jQuery('#from-text-fileuploader-progress .bar').css('width', progress + '%');
		}).on('fileuploaddone', function(e, data) {
			jQuery.each(data.result.files, function(index, file) {
				var callbackObj = new Object();
				callbackObj.value = file.name;
				callbackObj.success = true;
				doneCallback(callbackObj);
			});
		}).on('fileuploadfail', function(e, data) {
			jQuery.each(data.result.files, function(index, file) {
				var callbackObj = new Object();
				callbackObj.value = file.name;
				callbackObj.success = false;
				callbackObj.error = file.error;
				failCallback(callbackObj);
			});
		}).prop('disabled', !jQuery.support.fileInput).parent().addClass(
				jQuery.support.fileInput ? undefined : 'disabled');

	});

}

/* initOpenClose */
function initOpenClose() {
	jQuery('.accordion > ul > div > li').OpenClose({
		activeClass : 'active',
		opener : '>a',
		slider : 'div.slide',
		effect : 'slide',
		animSpeed : 500
	});
};
// open-close plugin
jQuery.fn.OpenClose = function(_options) {
	// default options
	var _options = jQuery.extend({
		activeClass : 'active',
		opener : '.opener',
		closeBtn : '.close-btn',
		slider : '.slide',
		animSpeed : 400,
		animStart : false,
		animEnd : false,
		effect : 'fade',
		outClickHide : false,
		event : 'click'
	}, _options);

	return this
			.each(function() {
				// options
				var _holder = jQuery(this);
				var _slideSpeed = _options.animSpeed;
				var _activeClass = _options.activeClass;
				var _opener = jQuery(_options.opener, _holder);
				var closeBtn = jQuery(_options.closeBtn, _holder);
				var _slider = jQuery(_options.slider, _holder);
				var _animStart = _options.animStart;
				var _animEnd = _options.animEnd;
				var _effect = _options.effect;
				var _event = _options.event;
				if (_slider.length) {
					_opener.bind(_event, function() {
						if (!_slider.is(':animated')) {
							if (typeof _animStart === 'function')
								_animStart();
							if (_holder.hasClass(_activeClass)
									&& !closeBtn.length) {
								_slider[_effect == 'fade' ? 'fadeOut'
										: 'slideUp'](_slideSpeed, function() {
									if (typeof _animEnd === 'function')
										_animEnd();
								});
								_holder.removeClass(_activeClass);
							} else {
								_holder.addClass(_activeClass);
								_slider[_effect == 'fade' ? 'fadeIn'
										: 'slideDown'](_slideSpeed, function() {
									if (typeof _animEnd === 'function')
										_animEnd();
								});
							}
						}
						return false;
					});
					if (closeBtn.length) {
						closeBtn
								.bind(
										_event,
										function() {
											if (!_slider.is(':animated')) {
												if (typeof _animStart === 'function')
													_animStart();
												if (_holder
														.hasClass(_activeClass)) {
													_slider[_effect == 'fade' ? 'fadeOut'
															: 'slideUp']
															(
																	_slideSpeed,
																	function() {
																		if (typeof _animEnd === 'function')
																			_animEnd();
																	});
													_holder
															.removeClass(_activeClass);
												}
											}
											return false;
										});
					}
					if (_holder.hasClass(_activeClass))
						_slider.show();
					else
						_slider.hide();
					if (_options.outClickHide) {
						jQuery('body')
								.bind(
										'click',
										function(e) {
											if (_holder.hasClass(_activeClass)) {
												if (!jQuery(e.target).parents()
														.is(_holder)) {
													_slider[_effect == 'fade' ? 'fadeOut'
															: 'slideUp']
															(
																	_slideSpeed,
																	function() {
																		if (typeof _animEnd === 'function')
																			_animEnd();
																	});
													_holder
															.removeClass(_activeClass);
												}
											}
										});
					}
					;
				}
			});
};

/* initSlider */
function initSlider() {
	jQuery('.slideshow').each(function() {
		var set = jQuery(this);
		if (this.load)
			return;
		else
			this.load = true;
		new SliderNV(set, {
			slider : 'div.slide-box > ul',
			effect : false,
			autoHeight : true,
			pagerLinks : 'ul.switcher > li',
			generatePagination : 'ul.switcher',
			autoRotation : true,
			switchTime : 5000
		});
	});
};
/* slider module */
(function($) {
	SliderNV = function() {
		this.init.apply(this, arguments)
	}
	SliderNV.prototype = {
		init : function(context, options) {
			/* default options */
			this.options = jQuery
					.extend(
							{
								sliderHolder : '>div',
								slider : '>ul',
								slides : '>li',
								pagerLinks : 'div.pager a',
								generatePagination : false,
								generatePaginationMarkup : '<li><a href="#">&amp;nbsp;</a></li>',
								btnPrev : 'a.btn-prev111',
								btnNext : 'a.btn-next111',
								activeClass : 'active',
								disabledClass : 'disabled',
								circleSlide : true,
								effect : true, // true == slide effect & false
								// == fade effect
								pauseClass : 'gallery-paused',
								pauseOnHover : true,
								autoRotation : false,
								switchTime : 5000,
								animSpeed : 650,
								easing : 'swing',
								pagerEvent : 'click',
								butttonEvent : 'click',
								beforeInit : function() {
								},
								afterInit : function() {
								},
								beforeAnimation : function() {
								},
								afterAnimation : function() {
								},
								vertical : false,
								reverse : false,
								step : false,
								startElement : 0,
								autoHeight : false
							}, options);
			if (!context)
				return;
			this.set = context;
			if (typeof this.options.beforeInit === 'function')
				this.options.beforeInit(this);
			this.elementInit();
			this.eventInit();
			this.startPosition(this.currentElement);
			if (typeof this.options.afterInit === 'function')
				this.options.afterInit(this);
			this.set.data('sliderNV', this);
		},
		elementInit : function() {
			this.sliderHolder = jQuery(this.options.sliderHolder, this.set);
			this.slider = jQuery(this.options.slider, this.set);
			this.slides = jQuery(this.options.slides, this.slider);
			this.reCalc();
			this.currentElement = this.prevElement = this.options.startElement || 0;
			this.pagination();
			this.stepCount = this.slides.length;
			this.autoSlide();
		},
		reCalc : function() {
			this.sumHeight = 0;
			this.sumWidth = 0;
			this.slides.each(jQuery.proxy(function(i, elem) {
				this.sumHeight += jQuery(elem).outerHeight(true);
				this.sumWidth += jQuery(elem).outerWidth(true);
			}, this));
		},
		eventInit : function() {
			this.set.find(this.options.btnPrev).unbind(
					this.options.butttonEvent).bind(this.options.butttonEvent,
					jQuery.proxy(function() {
						this.prev();
						return false;
					}, this));
			this.set.find(this.options.btnNext).unbind(
					this.options.butttonEvent).bind(this.options.butttonEvent,
					jQuery.proxy(function() {
						this.next();
						return false;
					}, this));
			if (this.options.pauseOnHover) {
				this.set.bind('mouseenter', jQuery.proxy(function() {
					this.set.addClass(this.options.pauseClass);
					clearTimeout(this.autoTimer);
				}, this)).bind('mouseleave', jQuery.proxy(function() {
					this.set.removeClass(this.options.pauseClass);
					this.autoSlide();
				}, this));
			}
			;
		},
		pagination : function() {
			if (this.options.generatePagination) {
				this.set.find(this.options.generatePagination).empty()
				this.slides
						.each(jQuery
								.proxy(
										function() {
											var temp = jQuery(this.options.generatePaginationMarkup);
											this.set
													.find(
															this.options.generatePagination)
													.append(temp);
										}, this));
			}
			;
			this.pagerLinks = jQuery(this.options.pagerLinks, this.set);
			if (this.pagerLinks) {
				this.pagerLinks.each(jQuery.proxy(function(idx, elem) {
					jQuery(elem).unbind(this.options.pagerEvent).bind(
							this.options.pagerEvent,
							jQuery.proxy(function() {
								if (!jQuery(elem).hasClass(
										this.options.activeClass)) {
									this.prevElement = this.currentElement;
									this.currentElement = idx;
									this.swichFunction(this.currentElement);
									this.set.data('sliderNV', this);
								}
								;
								return false;
							}, this));
				}, this));
			}
			;
		},
		prev : function() {
			this.prevElement = this.currentElement;
			if (this.currentElement > 0) {
				this.currentElement--;
			} else if (this.options.circleSlide) {
				this.currentElement = this.stepCount - 1;
			}
			;
			this.swichFunction(this.currentElement);
		},
		next : function() {
			this.prevElement = this.currentElement;
			if (this.currentElement < this.stepCount - 1) {
				this.currentElement++;
			} else if (this.options.circleSlide) {
				this.currentElement = 0;
			}
			;
			this.swichFunction(this.currentElement);
		},
		disableButton : function() {
			if (!this.options.circleSlide) {
				this.set.find(this.options.btnPrev).removeClass(
						this.options.disabledClass);
				this.set.find(this.options.btnNext).removeClass(
						this.options.disabledClass);
				if (this.currentElement == 0) {
					this.set.find(this.options.btnPrev).addClass(
							this.options.disabledClass);
				} else if (this.currentElement == this.stepCount - 1) {
					this.set.find(this.options.btnNext).addClass(
							this.options.disabledClass);
				}
				;
			}
			;
		},
		recalcOffsets : function(curr) {
			if (this.options.vertical) {
				/* slide vertical */
				if (this.options.step) {
					this.stepHeight = this.slides.eq(curr).outerHeight(true)
							* parseInt(this.options.step);
					this.stepCount = Math
							.ceil((this.sumHeight - this.sliderHolder.height())
									/ this.stepHeight) + 1;
					this.offset = -this.stepHeight * curr;
				} else {
					this.stepHeight = this.sliderHolder.height();
					this.stepCount = Math
							.ceil(this.sumHeight / this.stepHeight);
					this.offset = -this.stepHeight * curr;
					if (this.offset < this.stepHeight - this.sumHeight)
						this.offset = this.stepHeight - this.sumHeight;
				}
			} else {
				/* slide horizontal */
				if (this.options.step) {
					this.stepWidth = this.slides.eq(curr).outerWidth(true)
							* parseInt(this.options.step);
					this.stepCount = Math
							.ceil((this.sumWidth - this.sliderHolder.width())
									/ this.stepWidth) + 1;
					this.offset = -this.stepWidth * curr;
					if (this.offset < this.sliderHolder.width() - this.sumWidth)
						this.offset = this.sliderHolder.width() - this.sumWidth;
				} else {
					this.stepWidth = this.sliderHolder.width();
					this.stepCount = Math.ceil(this.sumWidth / this.stepWidth);
					this.offset = -this.stepWidth * curr;
					if (this.offset < this.stepWidth - this.sumWidth)
						this.offset = this.stepWidth - this.sumWidth;
				}
			}
			;
		},
		swichFunction : function(curr) {
			if (!this.slides.eq(curr).length) {
				if (this.prevElement == 0)
					this.currentElement = this.slides.length - 1;
				else if (this.prevElement == this.slides.length - 1)
					this.currentElement = 0;
				this.prevElement = this.currentElement;
			}
			if (!this.options.effect) {
				/* fade effect */
				if (typeof this.options.beforeAnimation === 'function')
					this.options.beforeAnimation(this);
				this.slides.filter(':visible').stop(false, true).fadeOut(
						this.options.animSpeed);
				this.slides
						.eq(this.currentElement)
						.stop(false, true)
						.fadeIn(
								this.options.animSpeed,
								jQuery
										.proxy(
												function() {
													if (typeof this.options.afterAnimation === 'function')
														this.options
																.afterAnimation(this);
													this.autoSlide();
													if (this.options.autoHeight)
														this.slider
																.css({
																	position : 'relative',
																	height : this.slides
																			.eq(
																					this.currentElement)
																			.outerHeight(
																					true)
																});
													this.set.data('sliderNV',
															this);
												}, this));
			} else {
				/* slide effect */
				var marginType = {};
				this.recalcOffsets(curr);
				marginType[this.options.vertical ? 'marginTop' : 'marginLeft'] = this.offset;
				if (typeof this.options.beforeAnimation === 'function')
					this.options.beforeAnimation(this);
				this.slider.animate(marginType, {
					queue : false,
					duration : this.options.animSpeed,
					complete : jQuery.proxy(function() {
						if (typeof this.options.afterAnimation === 'function')
							this.options.afterAnimation(this);
						this.autoSlide();
						this.set.data('sliderNV', this);
					}, this)
				});
			}
			;
			this.pagerLinks.removeClass(this.options.activeClass).eq(
					this.currentElement).addClass(this.options.activeClass);
			if (!this.options.circleSlide)
				this.disableButton();
			this.set.data('sliderNV', this);
		},
		startPosition : function(curr) {
			if (this.options.effect) {
				var marginType = {};
				this.recalcOffsets(curr);
				marginType[this.options.vertical ? 'marginTop' : 'marginLeft'] = this.offset;
				this.slider.css(marginType);
				this.slider.css({
					width : this.sumWidth
				});
			} else {
				this.slides.css({
					position : 'absolute',
					top : 0,
					left : 0
				});
				this.slider.css({
					position : 'relative'
				});
				if (this.options.autoHeight)
					this.slider.css({
						position : 'relative',
						height : this.slides.eq(this.currentElement)
								.outerHeight(true)
					});
				this.slides.hide().eq(this.currentElement).show();
			}
			;
			this.disableButton();
			this.pagerLinks.removeClass(this.options.activeClass).eq(
					this.currentElement).addClass(this.options.activeClass);
		},
		autoSlide : function() {
			if (this.options.autoRotation
					&& !this.set.hasClass(this.options.pauseClass)) {
				if (this.autoTimer)
					clearTimeout(this.autoTimer);
				this.autoTimer = setTimeout(jQuery.proxy(function() {
					if (this.options.reverse) {
						this.prev();
					} else {
						this.next();
					}
				}, this), this.options.switchTime + this.options.animSpeed);
			}
		}
	};
})(jQuery);

var VSA_scrollAreas = new Array();
var VSA_default_imagesPath = "js/gwt/lifteditor/images";
var VSA_default_btnUpImage = "button-up.gif";
var VSA_default_btnDownImage = "button-down.gif";
var VSA_default_scrollStep = 5;
var VSA_default_wheelSensitivity = 10;
var VSA_default_scrollbarPosition = 'right';// 'left','right','inline';
var VSA_default_scrollButtonHeight = 0;
var VSA_default_scrollbarWidth = 22;
var VSA_resizeTimer = 2000;
var VSA_touchFlag = isTouchDevice(); // true/false - move scroll with
// scrollable body

function VSA_initScrollbars() {
	if (!document.body.children)
		return;
	var scrollElements = VSA_getElements("vscrollable", "DIV", document,
			"class");
	for ( var i = 0; i < scrollElements.length; i++) {
		VSA_scrollAreas[i] = new VScrollArea(i, scrollElements[i]);
	}
}

function isTouchDevice() {
	try {
		document.createEvent("TouchEvent");
		return true;
	} catch (e) {
		return false;
	}
}

function touchHandler(event) {
	var touches = event.changedTouches, first = touches[0], type = "";
	switch (event.type) {
	case "touchstart":
		type = "mousedown";
		break;
	case "touchmove":
		type = "mousemove";
		break;
	case "touchend":
		type = "mouseup";
		break;
	default:
		return;
	}
	var simulatedEvent = document.createEvent("MouseEvent");
	simulatedEvent.initMouseEvent(type, true, true, window, 1, first.screenX,
			first.screenY, first.clientX, first.clientY, false, false, false,
			false, 0/* left */, null);
	first.target.dispatchEvent(simulatedEvent);
	event.preventDefault();
}

function VScrollArea(index, elem) // constructor
{
	this.index = index;
	this.element = elem;

	var attr = this.element.getAttribute("imagesPath");
	this.imagesPath = attr ? attr : VSA_default_imagesPath;

	attr = this.element.getAttribute("btnUpImage");
	this.btnUpImage = attr ? attr : VSA_default_btnUpImage;

	attr = this.element.getAttribute("btnDownImage");
	this.btnDownImage = attr ? attr : VSA_default_btnDownImage;

	attr = Number(this.element.getAttribute("scrollStep"));
	this.scrollStep = attr ? attr : VSA_default_scrollStep;

	attr = Number(this.element.getAttribute("wheelSensitivity"));
	this.wheelSensitivity = attr ? attr : VSA_default_wheelSensitivity;

	attr = this.element.getAttribute("scrollbarPosition");
	this.scrollbarPosition = attr ? attr : VSA_default_scrollbarPosition;

	attr = this.element.getAttribute("scrollButtonHeight");
	this.scrollButtonHeight = attr ? attr : VSA_default_scrollButtonHeight;

	attr = this.element.getAttribute("scrollbarWidth");
	this.scrollbarWidth = attr ? attr : VSA_default_scrollbarWidth;

	this.scrolling = false;

	this.iOffsetY = 0;
	this.scrollHeight = 0;
	this.scrollContent = null;
	this.scrollbar = null;
	this.scrollup = null;
	this.scrolldown = null;
	this.scrollslider = null;
	this.scroll = null;
	this.enableScrollbar = false;
	this.scrollFactor = 1;
	this.scrollingLimit = 0;
	this.topPosition = 0;

	// functions declaration
	this.init = VSA_init;
	this.scrollUp = VSA_scrollUp;
	this.scrollDown = VSA_scrollDown;
	this.createScrollBar = VSA_createScrollBar;
	this.scrollIt = VSA_scrollIt;

	this.init();
}

function VSA_init() {
	this.scrollContent = document.createElement("DIV");
	this.scrollContent.style.position = "absolute";
	this.scrollContent.style.overflow = "hidden";
	this.scrollContent.style.width = this.element.offsetWidth + "px";
	this.scrollContent.style.height = this.element.offsetHeight + "px";

	while (this.element.childNodes.length)
		this.scrollContent.appendChild(this.element.childNodes[0]);

	this.element.style.overflow = "hidden";
	this.element.style.display = "block";
	this.element.style.visibility = "visible";
	this.element.style.position = "relative";
	this.element.appendChild(this.scrollContent);

	this.scrollContent.className = 'scroll-content';

	this.element.index = this.index;
	this.element.over = false;

	var _this = this;

	if (document.all && !window.opera) {
		this.element.onmouseenter = function() {
			_this.element.over = true;
		};
		this.element.onmouseleave = function() {
			_this.element.over = false;
		}
	} else {
		this.element.onmouseover = function() {
			_this.element.over = true;
		};
		this.element.onmouseout = function() {
			_this.element.over = false;
		}
	}

	if (document.all) {
		this.element.onscroll = VSA_handleOnScroll;
		this.element.onresize = VSA_handleResize;
	} else {
		window.onresize = VSA_handleResize;
	}

	this.createScrollBar();

	if (window.addEventListener) {
		/* DOMMouseScroll is for mozilla. */
		this.element.addEventListener('DOMMouseScroll', VSA_handleMouseWheel,
				false);
	}
	/* IE/Opera. */
	this.element.onmousewheel = document.onmousewheel = VSA_handleMouseWheel;

	// move content by touch
	if (VSA_touchFlag) {
		_this.scrollContent.onmousedown = function(e) {
			var startY = e.pageY - getRealTop(_this.scrollContent);
			var origTop = _this.scrollContent.scrollTop;
			_this.scrollContent.onmousemove = function(e) {
				var moveY = e.pageY - getRealTop(_this.scrollContent);
				var iNewY = origTop - (moveY - startY);
				if (iNewY < 0)
					iNewY = 0;
				if (iNewY > _this.scrollContent.scrollHeight)
					iNewY = _this.scrollContent.scrollHeight;
				_this.scrollContent.scrollTop = iNewY;
				_this.scrollslider.style.top = 1 / _this.scrollFactor
						* Math.abs(_this.scrollContent.scrollTop)
						+ _this.scrollButtonHeight + "px";
			}
		}
		_this.scrollContent.onmouseup = function(e) {
			_this.scrollContent.onmousemove = null;
		}
		this.scrollContent.addEventListener("touchstart", touchHandler, true);
		this.scrollContent.addEventListener("touchmove", touchHandler, true);
		this.scrollContent.addEventListener("touchend", touchHandler, true);
	}
}

function VSA_createScrollBar() {
	if (this.scrollbar != null) {
		this.element.removeChild(this.scrollbar);
		this.scrollbar = null;
	}

	if (this.scrollContent.scrollHeight <= this.scrollContent.offsetHeight)
		this.enableScrollbar = false;
	else if (this.element.offsetHeight > 2 * this.scrollButtonHeight)
		this.enableScrollbar = true;
	else
		this.enableScrollbar = false;

	if (this.scrollContent.scrollHeight
			- Math.abs(this.scrollContent.scrollTop) < this.element.offsetHeight)
		this.scrollContent.style.top = 0;

	if (this.enableScrollbar) {
		this.scrollbar = document.createElement("DIV");
		this.element.appendChild(this.scrollbar);
		this.scrollbar.style.position = "absolute";
		this.scrollbar.style.top = "0px";
		this.scrollbar.style.height = this.element.offsetHeight + "px";
		this.scrollbar.style.width = this.scrollbarWidth + "px";

		this.scrollbar.className = 'vscroll-bar';

		if (this.scrollbarWidth != this.scrollbar.offsetWidth) {
			this.scrollbarWidth = this.scrollbar.offsetHeight;
		}

		this.scrollbarWidth = this.scrollbar.offsetWidth;

		if (this.scrollbarPosition == 'left') {
			this.scrollContent.style.left = this.scrollbarWidth + 5 + "px";
			this.scrollContent.style.width = this.element.offsetWidth
					- this.scrollbarWidth - 5 + "px";
		} else if (this.scrollbarPosition == 'right') {
			this.scrollbar.style.left = this.element.offsetWidth
					- this.scrollbarWidth + "px";
			this.scrollContent.style.width = this.element.offsetWidth
					- this.scrollbarWidth - 5 + "px";
		}

		// create scroll up button
		this.scrollup = document.createElement("DIV");
		this.scrollup.index = this.index;
		this.scrollup.onmousedown = VSA_handleBtnUpMouseDown;
		this.scrollup.onmouseup = VSA_handleBtnUpMouseUp;
		this.scrollup.onmouseout = VSA_handleBtnUpMouseOut;

		if (VSA_touchFlag) {
			this.scrollup.addEventListener("touchstart", touchHandler, true);
			this.scrollup.addEventListener("touchend", touchHandler, true);
		}

		this.scrollup.style.position = "absolute";
		this.scrollup.style.top = "0px";
		this.scrollup.style.left = "0px";
		this.scrollup.style.height = this.scrollButtonHeight + "px";
		this.scrollup.style.width = this.scrollbarWidth + "px";

		this.scrollup.innerHTML = '<img src="' + this.imagesPath + '/'
				+ this.btnUpImage + '" border="0"/>';
		this.scrollbar.appendChild(this.scrollup);

		this.scrollup.className = 'vscroll-up';

		if (this.scrollButtonHeight != this.scrollup.offsetHeight) {
			this.scrollButtonHeight = this.scrollup.offsetHeight;
		}

		// create scroll down button
		this.scrolldown = document.createElement("DIV");
		this.scrolldown.index = this.index;
		this.scrolldown.onmousedown = VSA_handleBtnDownMouseDown;
		this.scrolldown.onmouseup = VSA_handleBtnDownMouseUp;
		this.scrolldown.onmouseout = VSA_handleBtnDownMouseOut;

		if (VSA_touchFlag) {
			this.scrolldown.addEventListener("touchstart", touchHandler, true);
			this.scrolldown.addEventListener("touchend", touchHandler, true);
		}

		this.scrolldown.style.position = "absolute";
		this.scrolldown.style.left = "0px";
		this.scrolldown.style.top = this.scrollbar.offsetHeight
				- this.scrollButtonHeight + "px";
		this.scrolldown.style.width = this.scrollbarWidth + "px";
		this.scrolldown.innerHTML = '<img src="' + this.imagesPath + '/'
				+ this.btnDownImage + '" border="0"/>';
		this.scrollbar.appendChild(this.scrolldown);

		this.scrolldown.className = 'vscroll-down';

		// create scroll
		this.scroll = document.createElement("DIV");
		this.scroll.index = this.index;
		this.scroll.style.position = "absolute";
		this.scroll.style.zIndex = 0;
		this.scroll.style.textAlign = "center";
		this.scroll.style.top = this.scrollButtonHeight + "px";
		this.scroll.style.left = "0px";
		this.scroll.style.width = this.scrollbarWidth + "px";

		var h = this.scrollbar.offsetHeight - 2 * this.scrollButtonHeight;
		this.scroll.style.height = ((h > 0) ? h : 0) + "px";

		this.scroll.innerHTML = '';
		this.scroll.onclick = VSA_handleScrollbarClick;
		this.scrollbar.appendChild(this.scroll);
		this.scroll.style.overflow = "hidden";

		this.scroll.className = "vscroll-line";

		// create slider
		this.scrollslider = document.createElement("DIV");
		this.scrollslider.index = this.index;
		this.scrollslider.style.position = "absolute";
		this.scrollslider.style.zIndex = 1000;
		this.scrollslider.style.textAlign = "center";
		this.scrollslider.innerHTML = '<div id="scrollslider'
				+ this.index
				+ '" style="padding:0;margin:0;"><div class="scroll-bar-top"></div><div class="scroll-bar-bottom"></div></div>';
		this.scrollbar.appendChild(this.scrollslider);

		this.subscrollslider = document.getElementById("scrollslider"
				+ this.index);
		this.subscrollslider.style.height = Math
				.round((this.scrollContent.offsetHeight / this.scrollContent.scrollHeight)
						* (this.scrollbar.offsetHeight - 2 * this.scrollButtonHeight))
				+ "px";

		this.scrollslider.className = "vscroll-slider";

		this.scrollHeight = this.scrollbar.offsetHeight - 2
				* this.scrollButtonHeight - this.scrollslider.offsetHeight;
		this.scrollFactor = (this.scrollContent.scrollHeight - this.scrollContent.offsetHeight)
				/ this.scrollHeight;
		this.topPosition = getRealTop(this.scrollbar) + this.scrollButtonHeight;
		/*
		 * this.scrollbarHeight = this.scrollbar.offsetHeight -
		 * 2*this.scrollButtonHeight - this.scrollslider.offsetHeight;
		 */

		this.scrollslider.style.top = /*
										 * 1 / this.scrollFactor *
										 * Math.abs(this.scrollContent.offsetTop) +
										 */this.scrollButtonHeight + "px";
		this.scrollslider.style.left = "0px";
		this.scrollslider.style.width = "100%";
		this.scrollslider.onmousedown = VSA_handleSliderMouseDown;
		if (VSA_touchFlag) {
			this.scrollslider
					.addEventListener("touchstart", touchHandler, true);
		}
		if (document.all)
			this.scrollslider.onmouseup = VSA_handleSliderMouseUp;
	} else
		this.scrollContent.style.width = this.element.offsetWidth + "px";
}

function VSA_handleBtnUpMouseDown() {
	var sa = VSA_scrollAreas[this.index];
	sa.scrolling = true;
	sa.scrollUp();
}

function VSA_handleBtnUpMouseUp() {
	VSA_scrollAreas[this.index].scrolling = false;
}

function VSA_handleBtnUpMouseOut() {
	VSA_scrollAreas[this.index].scrolling = false;
}

function VSA_handleBtnDownMouseDown() {
	var sa = VSA_scrollAreas[this.index];
	sa.scrolling = true;
	sa.scrollDown();
}

function VSA_handleBtnDownMouseUp() {
	VSA_scrollAreas[this.index].scrolling = false;
}

function VSA_handleBtnDownMouseOut() {
	VSA_scrollAreas[this.index].scrolling = false;
}

function VSA_scrollIt() {
	this.scrollContent.scrollTop = this.scrollFactor
			* ((this.scrollslider.offsetTop + this.scrollslider.offsetHeight / 2)
					- this.scrollButtonHeight - this.scrollslider.offsetHeight / 2);
}

function VSA_scrollUp() {
	if (this.scrollingLimit > 0) {
		this.scrollingLimit--;
		if (this.scrollingLimit == 0)
			this.scrolling = false;
	}
	if (!this.scrolling)
		return;
	if (this.scrollContent.scrollTop - this.scrollStep > 0) {
		this.scrollContent.scrollTop -= this.scrollStep;
		this.scrollslider.style.top = 1 / this.scrollFactor
				* Math.abs(this.scrollContent.scrollTop)
				+ this.scrollButtonHeight + "px";
	} else {
		this.scrollContent.scrollTop = "0";
		this.scrollslider.style.top = this.scrollButtonHeight + "px";
		return;
	}
	setTimeout("VSA_Ext_scrollUp(" + this.index + ")", 30);
}

function VSA_Ext_scrollUp(index) {
	VSA_scrollAreas[index].scrollUp();
}

function VSA_scrollDown() {
	if (this.scrollingLimit > 0) {
		this.scrollingLimit--;
		if (this.scrollingLimit == 0)
			this.scrolling = false;
	}
	if (!this.scrolling)
		return;

	this.scrollContent.scrollTop += this.scrollStep;
	this.scrollslider.style.top = 1 / this.scrollFactor
			* Math.abs(this.scrollContent.scrollTop) + this.scrollButtonHeight
			+ "px";

	if (this.scrollContent.scrollTop >= (this.scrollContent.scrollHeight - this.scrollContent.offsetHeight)) {
		this.scrollContent.scrollTop = (this.scrollContent.scrollHeight - this.scrollContent.offsetHeight);
		this.scrollslider.style.top = this.scrollbar.offsetHeight
				- this.scrollButtonHeight - this.scrollslider.offsetHeight
				+ "px";
		return;
	}

	setTimeout("VSA_Ext_scrollDown(" + this.index + ")", 30);
}

function VSA_Ext_scrollDown(index) {
	VSA_scrollAreas[index].scrollDown();
}

function VSA_handleMouseMove(evt) {
	var sa = VSA_scrollAreas[((document.all && !window.opera) ? this.index
			: document.documentElement.scrollAreaIndex)];
	var posy = 0;
	if (!evt)
		var evt = window.event;

	if (evt.pageY)
		posy = evt.pageY;
	else if (evt.clientY)
		posy = evt.clientY;

	if (document.all && !window.opera) {
		if (!document.addEventListener) {
			posy += document.documentElement.scrollTop;
		}
	}

	var iNewY = posy - sa.iOffsetY - getRealTop(sa.scrollbar)
			- sa.scrollButtonHeight;
	iNewY += sa.scrollButtonHeight;

	if (iNewY < sa.scrollButtonHeight)
		iNewY = sa.scrollButtonHeight;
	if (iNewY > (sa.scrollbar.offsetHeight - sa.scrollButtonHeight)
			- sa.scrollslider.offsetHeight)
		iNewY = (sa.scrollbar.offsetHeight - sa.scrollButtonHeight)
				- sa.scrollslider.offsetHeight;

	sa.scrollslider.style.top = iNewY + "px";

	sa.scrollIt();
}

function VSA_handleSliderMouseDown(evt) {
	if (!(document.uniqueID && document.compatMode && !window.XMLHttpRequest)) {
		document.onselectstart = function() {
			return false;
		}
		document.onmousedown = function() {
			return false;
		}
	}

	var sa = VSA_scrollAreas[this.index];
	if (document.all && !window.opera) {
		sa.scrollslider.setCapture()
		sa.iOffsetY = event.offsetY;
		sa.scrollslider.onmousemove = VSA_handleMouseMove;
		if (VSA_touchFlag) {
			sa.scrollslider.addEventListener("touchmove", touchHandler, true);
		}
	} else {
		if (window.opera) {
			sa.iOffsetY = event.offsetY;
		} else {
			sa.iOffsetY = evt.layerY;
		}
		document.documentElement.scrollAreaIndex = sa.index;
		document.documentElement.addEventListener("mousemove",
				VSA_handleMouseMove, true);
		document.documentElement.addEventListener("mouseup",
				VSA_handleSliderMouseUp, true);
		if (VSA_touchFlag) {
			document.documentElement.addEventListener("touchmove",
					touchHandler, true);
			document.documentElement.addEventListener("touchend", touchHandler,
					true);
		}
	}
	return false;
}

function VSA_handleSliderMouseUp() {
	if (!(document.uniqueID && document.compatMode && !window.XMLHttpRequest)) {
		document.onmousedown = null;
		document.onselectstart = null;
	}

	if (document.all && !window.opera) {
		var sa = VSA_scrollAreas[this.index];
		sa.scrollslider.onmousemove = null;
		sa.scrollslider.releaseCapture();
		sa.scrollIt();
	} else {
		var sa = VSA_scrollAreas[document.documentElement.scrollAreaIndex];
		document.documentElement.removeEventListener("mousemove",
				VSA_handleMouseMove, true);
		document.documentElement.removeEventListener("mouseup",
				VSA_handleSliderMouseUp, true);
		if (VSA_touchFlag) {
			document.documentElement.removeEventListener("touchmove",
					touchHandler, true);
			document.documentElement.removeEventListener("touchend",
					touchHandler, true);
		}
		sa.scrollIt();
	}
	return false;
}

function VSA_handleResize() {
	if (VSA_resizeTimer) {
		clearTimeout(VSA_resizeTimer);
		VSA_resizeTimer = 0;
	}
	VSA_resizeTimer = setTimeout("VSA_performResizeEvent()", 100);
}

function VSA_performResizeEvent() {
	for ( var i = 0; i < VSA_scrollAreas.length; i++)
		VSA_scrollAreas[i].createScrollBar();
}
function VSA_handleMouseWheel(event) {
	if (this.index != null) {
		var sa = VSA_scrollAreas[this.index];
		if (sa.scrollbar == null)
			return;
		sa.scrolling = true;
		sa.scrollingLimit = sa.wheelSensitivity;

		var delta = 0;
		if (!event) /* For IE. */
			event = window.event;
		if (event.wheelDelta) { /* IE/Opera. */
			delta = event.wheelDelta / 120;
			/* if (window.opera) delta = -delta; */
		} else if (event.detail) { /* Mozilla case. */
			delta = -event.detail / 3;
		}

		if (delta && sa.element.over) {
			if (delta > 0) {
				sa.scrollUp();
			} else {
				sa.scrollDown();
			}
			if (event.preventDefault) {
				event.preventDefault();
			}
			event.returnValue = false;
		}
	}
}

function VSA_handleSelectStart() {
	event.returnValue = false;
}

function VSA_handleScrollbarClick(evt) {
	var sa = VSA_scrollAreas[this.index];
	var offsetY = (document.all ? event.offsetY : evt.layerY);

	if (offsetY < (sa.scrollButtonHeight + sa.scrollslider.offsetHeight / 2))
		sa.scrollslider.style.top = sa.scrollButtonHeight + "px";
	else if (offsetY > (sa.scrollbar.offsetHeight - sa.scrollButtonHeight - sa.scrollslider.offsetHeight))
		sa.scrollslider.style.top = sa.scrollbar.offsetHeight
				- sa.scrollButtonHeight - sa.scrollslider.offsetHeight + "px";
	else {
		sa.scrollslider.style.top = offsetY + sa.scrollButtonHeight
				- sa.scrollslider.offsetHeight / 2 + "px";
	}
	sa.scrollIt();
}

function VSA_handleOnScroll() {
	// event.srcElement.doScroll("pageUp");
}

// --- common functions ----

function VSA_getElements(attrValue, tagName, ownerNode, attrName) // get
// Elements
// By
// Attribute
// Name
{
	if (!tagName)
		tagName = "*";
	if (!ownerNode)
		ownerNode = document;
	if (!attrName)
		attrName = "name";
	var result = [];
	var nl = ownerNode.getElementsByTagName(tagName);
	for ( var i = 0; i < nl.length; i++) {
		// if (nl.item(i).getAttribute(attrName) == attrValue)
		// result.push(nl.item(i));
		if (nl.item(i).className.indexOf(attrValue) != -1)
			result.push(nl.item(i));
	}
	return result;
}

function getRealTop(obj) {
	var posTop = 0;
	while (obj.offsetParent) {
		posTop += obj.offsetTop;
		obj = obj.offsetParent;
	}
	return posTop;
};

// clear inputs on focus
function initInputs() {
	// replace options
	var opt = {
		clearInputs : true,
		clearTextareas : true,
		clearPasswords : true
	}
	// collect all items
	var inputs = [].concat(PlaceholderInput.convertToArray(document
			.getElementsByTagName('input')), PlaceholderInput
			.convertToArray(document.getElementsByTagName('textarea')));
	// apply placeholder class on inputs
	for ( var i = 0; i < inputs.length; i++) {
		if (inputs[i].className.indexOf('default') < 0) {
			var inputType = PlaceholderInput.getInputType(inputs[i]);
			if ((opt.clearInputs && inputType === 'text')
					|| (opt.clearTextareas && inputType === 'textarea')
					|| (opt.clearPasswords && inputType === 'password')) {
				new PlaceholderInput({
					element : inputs[i],
					wrapWithElement : false,
					showUntilTyping : false,
					getParentByClass : false,
					placeholderAttr : 'value'
				});
			}
		}
	}
}

// input type placeholder class
;
(function() {
	PlaceholderInput = function() {
		this.options = {
			element : null,
			showUntilTyping : false,
			wrapWithElement : false,
			getParentByClass : false,
			placeholderAttr : 'value',
			inputFocusClass : 'focus',
			inputActiveClass : 'text-active',
			parentFocusClass : 'parent-focus',
			parentActiveClass : 'parent-active',
			labelFocusClass : 'label-focus',
			labelActiveClass : 'label-active',
			fakeElementClass : 'input-placeholder-text'
		}
		this.init.apply(this, arguments);
	}
	PlaceholderInput.convertToArray = function(collection) {
		var arr = [];
		for ( var i = 0, ref = arr.length = collection.length; i < ref; i++) {
			arr[i] = collection[i];
		}
		return arr;
	}
	PlaceholderInput.getInputType = function(input) {
		return (input.type ? input.type : input.tagName).toLowerCase();
	}
	PlaceholderInput.prototype = {
		init : function(opt) {
			this.setOptions(opt);
			if (this.element && this.element.PlaceholderInst) {
				this.element.PlaceholderInst.refreshClasses();
			} else {
				this.element.PlaceholderInst = this;
				if (this.elementType == 'text'
						|| this.elementType == 'password'
						|| this.elementType == 'textarea') {
					this.initElements();
					this.attachEvents();
					this.refreshClasses();
				}
			}
		},
		setOptions : function(opt) {
			for ( var p in opt) {
				if (opt.hasOwnProperty(p)) {
					this.options[p] = opt[p];
				}
			}
			if (this.options.element) {
				this.element = this.options.element;
				this.elementType = PlaceholderInput.getInputType(this.element);
				this.wrapWithElement = (this.elementType === 'password'
						|| this.options.showUntilTyping ? true
						: this.options.wrapWithElement);
				this
						.setOrigValue(this.options.placeholderAttr == 'value' ? this.element.defaultValue
								: this.element
										.getAttribute(this.options.placeholderAttr));
			}
		},
		setOrigValue : function(value) {
			this.origValue = value;
		},
		initElements : function() {
			// create fake element if needed
			if (this.wrapWithElement) {
				this.element.value = '';
				this.element.removeAttribute(this.options.placeholderAttr);
				this.fakeElement = document.createElement('span');
				this.fakeElement.className = this.options.fakeElementClass;
				this.fakeElement.innerHTML += this.origValue;
				this.fakeElement.style.color = getStyle(this.element, 'color');
				this.fakeElement.style.position = 'absolute';
				this.element.parentNode.insertBefore(this.fakeElement,
						this.element);
			}
			// get input label
			if (this.element.id) {
				this.labels = document.getElementsByTagName('label');
				for ( var i = 0; i < this.labels.length; i++) {
					if (this.labels[i].htmlFor === this.element.id) {
						this.labelFor = this.labels[i];
						break;
					}
				}
			}
			// get parent node (or parentNode by className)
			this.elementParent = this.element.parentNode;
			if (typeof this.options.parentByClass === 'string') {
				var el = this.element;
				while (el.parentNode) {
					if (hasClass(el.parentNode, this.options.parentByClass)) {
						this.elementParent = el.parentNode;
						break;
					} else {
						el = el.parentNode;
					}
				}
			}
		},
		attachEvents : function() {
			this.element.onfocus = bindScope(this.focusHandler, this);
			this.element.onblur = bindScope(this.blurHandler, this);
			if (this.options.showUntilTyping) {
				this.element.onkeydown = bindScope(this.typingHandler, this);
				this.element.onpaste = bindScope(this.typingHandler, this);
			}
			if (this.wrapWithElement)
				this.fakeElement.onclick = bindScope(this.focusSetter, this);
		},
		togglePlaceholderText : function(state) {
			if (this.wrapWithElement) {
				this.fakeElement.style.display = state ? '' : 'none';
			} else {
				this.element.value = state ? this.origValue : '';
			}
		},
		focusSetter : function() {
			this.element.focus();
		},
		focusHandler : function() {
			this.focused = true;
			if (!this.element.value.length
					|| this.element.value === this.origValue) {
				if (!this.options.showUntilTyping) {
					this.togglePlaceholderText(false);
				}
			}
			this.refreshClasses();
		},
		blurHandler : function() {
			this.focused = false;
			if (!this.element.value.length
					|| this.element.value === this.origValue) {
				this.togglePlaceholderText(true);
			}
			this.refreshClasses();
		},
		typingHandler : function() {
			setTimeout(bindScope(function() {
				if (this.element.value.length) {
					this.togglePlaceholderText(false);
					this.refreshClasses();
				}
			}, this), 10);
		},
		refreshClasses : function() {
			this.textActive = this.focused
					|| (this.element.value.length && this.element.value !== this.origValue);
			this.setStateClass(this.element, this.options.inputFocusClass,
					this.focused);
			this.setStateClass(this.elementParent,
					this.options.parentFocusClass, this.focused);
			this.setStateClass(this.labelFor, this.options.labelFocusClass,
					this.focused);
			this.setStateClass(this.element, this.options.inputActiveClass,
					this.textActive);
			this.setStateClass(this.elementParent,
					this.options.parentActiveClass, this.textActive);
			this.setStateClass(this.labelFor, this.options.labelActiveClass,
					this.textActive);
		},
		setStateClass : function(el, cls, state) {
			if (!el)
				return;
			else if (state)
				addClass(el, cls);
			else
				removeClass(el, cls);
		}
	}

	// utility functions
	function hasClass(el, cls) {
		return el.className ? el.className.match(new RegExp('(\\s|^)' + cls
				+ '(\\s|$)')) : false;
	}
	function addClass(el, cls) {
		if (!hasClass(el, cls))
			el.className += " " + cls;
	}
	function removeClass(el, cls) {
		if (hasClass(el, cls)) {
			el.className = el.className.replace(new RegExp('(\\s|^)' + cls
					+ '(\\s|$)'), ' ');
		}
	}
	function bindScope(f, scope) {
		return function() {
			return f.apply(scope, arguments)
		}
	}
	function getStyle(el, prop) {
		if (document.defaultView && document.defaultView.getComputedStyle) {
			return document.defaultView.getComputedStyle(el, null)[prop];
		} else if (el.currentStyle) {
			return el.currentStyle[prop];
		} else {
			return el.style[prop];
		}
	}
}());

// mobile browsers detect
browserPlatform = {
	platforms : [ {
		uaString : [ 'symbian', 'midp' ],
		cssFile : 'symbian.css'
	}, // Symbian
	// phones
	{
		uaString : [ 'opera', 'mobi' ],
		cssFile : 'opera.css'
	}, // Opera Mobile
	{
		uaString : [ 'msie', 'ppc' ],
		cssFile : 'ieppc.css'
	}, // IE Mobile <6
	{
		uaString : 'iemobile',
		cssFile : 'iemobile.css'
	}, // IE Mobile 6+
	{
		uaString : 'webos',
		cssFile : 'webos.css'
	}, // Palm WebOS
	{
		uaString : 'Android',
		cssFile : 'android.css'
	}, // Android
	{
		uaString : [ 'BlackBerry', '/6.0', 'mobi' ],
		cssFile : 'blackberry6.css'
	}, // Blackberry
	// 6
	{
		uaString : [ 'BlackBerry', '/7.0', 'mobi' ],
		cssFile : 'blackberry7.css'
	}, // Blackberry
	// 7+
	{
		uaString : 'ipad',
		cssFile : 'ipad.css'
	}, // iPad
	{
		uaString : [ 'safari', 'mobi' ],
		cssFile : 'safari.css'
	} // iPhone and
	// other webkit
	// browsers
	],
	options : {
		cssPath : 'css/',
		mobileCSS : 'allmobile.css'
	},
	init : function() {
		this.checkMobile();
		this.parsePlatforms();
		return this;
	},
	checkMobile : function() {
		if (this.uaMatch('mobi') || this.uaMatch('midp') || this.uaMatch('ppc')
				|| this.uaMatch('webos')) {
			this.attachStyles({
				cssFile : this.options.mobileCSS
			});
		}
	},
	parsePlatforms : function() {
		for ( var i = 0; i < this.platforms.length; i++) {
			if (typeof this.platforms[i].uaString === 'string') {
				if (this.uaMatch(this.platforms[i].uaString)) {
					this.attachStyles(this.platforms[i]);
					break;
				}
			} else {
				for ( var j = 0, allMatch = true; j < this.platforms[i].uaString.length; j++) {
					if (!this.uaMatch(this.platforms[i].uaString[j])) {
						allMatch = false;
					}
				}
				if (allMatch) {
					this.attachStyles(this.platforms[i]);
					break;
				}
			}
		}
	},
	attachStyles : function(platform) {
		var head = document.getElementsByTagName('head')[0], fragment;
		var cssText = '<link rel="stylesheet" href="' + this.options.cssPath
				+ platform.cssFile + '" type="text/css"/>';
		var miscText = platform.miscHead;
		if (platform.cssFile) {
			if (document.body) {
				fragment = document.createElement('div');
				fragment.innerHTML = cssText;
				head.appendChild(fragment.childNodes[0]);
			} else {
				document.write(cssText);
			}
		}
		if (platform.miscHead) {
			if (document.body) {
				fragment = document.createElement('div');
				fragment.innerHTML = miscText;
				head.appendChild(fragment.childNodes[0]);
			} else {
				document.write(miscText);
			}
		}
	},
	uaMatch : function(str) {
		if (!this.ua) {
			this.ua = navigator.userAgent.toLowerCase();
		}
		return this.ua.indexOf(str.toLowerCase()) != -1;
	}
}.init();

// custom forms script
var maxVisibleOptions = 20;
var all_selects = false;
var active_select = null;
var selectText = "please select";

function initCustomForms() {
	getElements();
	separateElements();
	replaceRadios();
	replaceCheckboxes();
	replaceSelects();

	// hide drop when scrolling or resizing window
	if (window.addEventListener) {
		window.addEventListener("scroll", hideActiveSelectDrop, false);
		window.addEventListener("resize", hideActiveSelectDrop, false);
	} else if (window.attachEvent) {
		window.attachEvent("onscroll", hideActiveSelectDrop);
		window.attachEvent("onresize", hideActiveSelectDrop);
	}
}

function refreshCustomForms() {
	// remove prevously created elements
	if (window.inputs) {
		for ( var i = 0; i < checkboxes.length; i++) {
			if (checkboxes[i].checked) {
				checkboxes[i]._ca.className = "checkboxAreaChecked";
			} else {
				checkboxes[i]._ca.className = "checkboxArea";
			}
		}
		for ( var i = 0; i < radios.length; i++) {
			if (radios[i].checked) {
				radios[i]._ra.className = "radioAreaChecked";
			} else {
				radios[i]._ra.className = "radioArea";
			}
		}
		for ( var i = 0; i < selects.length; i++) {
			var newText = document.createElement('div');
			if (selects[i].options[selects[i].selectedIndex].title
					.indexOf('image') != -1) {
				newText.innerHTML = '<img src="'
						+ selects[i].options[selects[i].selectedIndex].title
						+ '" alt="" />';
				newText.innerHTML += '<span>'
						+ selects[i].options[selects[i].selectedIndex].text
						+ '</span>';
			} else {
				newText.innerHTML = selects[i].options[selects[i].selectedIndex].text;
			}
			document.getElementById("mySelectText" + i).innerHTML = newText.innerHTML;
		}
	}
}

// getting all the required elements
function getElements() {
	// remove prevously created elements
	if (window.inputs) {
		for ( var i = 0; i < inputs.length; i++) {
			inputs[i].className = inputs[i].className.replace('outtaHere', '');
			if (inputs[i]._ca)
				inputs[i]._ca.parentNode.removeChild(inputs[i]._ca);
			else if (inputs[i]._ra)
				inputs[i]._ra.parentNode.removeChild(inputs[i]._ra);
		}
		for (i = 0; i < selects.length; i++) {
			selects[i].replaced = null;
			selects[i].className = selects[i].className
					.replace('outtaHere', '');
			selects[i]._optionsDiv._parent.parentNode
					.removeChild(selects[i]._optionsDiv._parent);
			selects[i]._optionsDiv.parentNode
					.removeChild(selects[i]._optionsDiv);
		}
	}

	// reset state
	inputs = new Array();
	selects = new Array();
	labels = new Array();
	radios = new Array();
	radioLabels = new Array();
	checkboxes = new Array();
	checkboxLabels = new Array();
	for ( var nf = 0; nf < document.getElementsByTagName("form").length; nf++) {
		if (document.forms[nf].className.indexOf("default") < 0) {
			for ( var nfi = 0; nfi < document.forms[nf]
					.getElementsByTagName("input").length; nfi++) {
				inputs
						.push(document.forms[nf].getElementsByTagName("input")[nfi]);
			}
			for ( var nfl = 0; nfl < document.forms[nf]
					.getElementsByTagName("label").length; nfl++) {
				labels
						.push(document.forms[nf].getElementsByTagName("label")[nfl]);
			}
			for ( var nfs = 0; nfs < document.forms[nf]
					.getElementsByTagName("select").length; nfs++) {
				selects
						.push(document.forms[nf].getElementsByTagName("select")[nfs]);
			}
		}
	}
}

// separating all the elements in their respective arrays
function separateElements() {
	var r = 0;
	var c = 0;
	var t = 0;
	var rl = 0;
	var cl = 0;
	var tl = 0;
	var b = 0;
	for ( var q = 0; q < inputs.length; q++) {
		if (inputs[q].type == "radio") {
			radios[r] = inputs[q];
			++r;
			for ( var w = 0; w < labels.length; w++) {
				if ((inputs[q].id) && labels[w].htmlFor == inputs[q].id) {
					radioLabels[rl] = labels[w];
					++rl;
				}
			}
		}
		if (inputs[q].type == "checkbox") {
			checkboxes[c] = inputs[q];
			++c;
			for ( var w = 0; w < labels.length; w++) {
				if ((inputs[q].id) && (labels[w].htmlFor == inputs[q].id)) {
					checkboxLabels[cl] = labels[w];
					++cl;
				}
			}
		}
	}
}

// replacing radio buttons
function replaceRadios() {
	for ( var q = 0; q < radios.length; q++) {
		radios[q].className += " outtaHere";
		var radioArea = document.createElement("div");
		if (radios[q].checked) {
			radioArea.className = "radioAreaChecked";
		} else {
			radioArea.className = "radioArea";
		}
		radioArea.id = "myRadio" + q;
		radios[q].parentNode.insertBefore(radioArea, radios[q]);
		radios[q]._ra = radioArea;

		radioArea.onclick = new Function('rechangeRadios(' + q + ')');
		if (radioLabels[q]) {
			if (radios[q].checked) {
				radioLabels[q].className += "radioAreaCheckedLabel";
			}
			radioLabels[q].onclick = new Function('rechangeRadios(' + q + ')');
		}
	}
	return true;
}

// checking radios
function checkRadios(who) {
	var what = radios[who]._ra;
	for ( var q = 0; q < radios.length; q++) {
		if ((radios[q]._ra.className == "radioAreaChecked")
				&& (radios[q]._ra.nextSibling.name == radios[who].name)) {
			radios[q]._ra.className = "radioArea";
		}
	}
	what.className = "radioAreaChecked";
}

// changing radios
function changeRadios(who) {
	if (radios[who].checked) {
		for ( var q = 0; q < radios.length; q++) {
			if (radios[q].name == radios[who].name) {
				radios[q].checked = false;
			}
			radios[who].checked = true;
			checkRadios(who);
		}
	}
}

// rechanging radios
function rechangeRadios(who) {
	if (!radios[who].checked) {
		for ( var q = 0; q < radios.length; q++) {
			if (radios[q].name == radios[who].name) {
				radios[q].checked = false;
				if (radioLabels[q])
					radioLabels[q].className = radioLabels[q].className
							.replace("radioAreaCheckedLabel", "");
			}
		}
		radios[who].checked = true;
		if (radioLabels[who]
				&& radioLabels[who].className.indexOf("radioAreaCheckedLabel") < 0) {
			radioLabels[who].className += " radioAreaCheckedLabel";
		}
		checkRadios(who);

		if (window.$ && window.$.fn) {
			$(radios[who]).trigger('change');
		}
	}
}

// replacing checkboxes
function replaceCheckboxes() {
	for ( var q = 0; q < checkboxes.length; q++) {
		checkboxes[q].className += " outtaHere";
		var checkboxArea = document.createElement("div");
		if (checkboxes[q].checked) {
			checkboxArea.className = "checkboxAreaChecked";
			if (checkboxLabels[q]) {
				checkboxLabels[q].className += " checkboxAreaCheckedLabel"
			}
		} else {
			checkboxArea.className = "checkboxArea";
		}
		checkboxArea.id = "myCheckbox" + q;
		checkboxes[q].parentNode.insertBefore(checkboxArea, checkboxes[q]);
		checkboxes[q]._ca = checkboxArea;
		checkboxArea.onclick = new Function('rechangeCheckboxes(' + q + ')');
		if (checkboxLabels[q]) {
			checkboxLabels[q].onclick = new Function('changeCheckboxes(' + q
					+ ')');
		}
		checkboxes[q].onkeydown = checkEvent;
	}
	return true;
}

// checking checkboxes
function checkCheckboxes(who, action) {
	var what = checkboxes[who]._ca;
	if (action == true) {
		what.className = "checkboxAreaChecked";
		what.checked = true;
	}
	if (action == false) {
		what.className = "checkboxArea";
		what.checked = false;
	}
	if (checkboxLabels[who]) {
		if (checkboxes[who].checked) {
			if (checkboxLabels[who].className
					.indexOf("checkboxAreaCheckedLabel") < 0) {
				checkboxLabels[who].className += " checkboxAreaCheckedLabel";
			}
		} else {
			checkboxLabels[who].className = checkboxLabels[who].className
					.replace("checkboxAreaCheckedLabel", "");
		}
	}

}

// changing checkboxes
function changeCheckboxes(who) {
	setTimeout(function() {
		if (checkboxes[who].checked) {
			checkCheckboxes(who, true);
		} else {
			checkCheckboxes(who, false);
		}
	}, 10);
}

// rechanging checkboxes
function rechangeCheckboxes(who) {
	var tester = false;
	if (checkboxes[who].checked == true) {
		tester = false;
	} else {
		tester = true;
	}
	checkboxes[who].checked = tester;
	checkCheckboxes(who, tester);
	if (window.$ && window.$.fn) {
		$(checkboxes[who]).trigger('change');
	}
}

// check event
function checkEvent(e) {
	if (!e)
		var e = window.event;
	if (e.keyCode == 32) {
		for ( var q = 0; q < checkboxes.length; q++) {
			if (this == checkboxes[q]) {
				changeCheckboxes(q);
			}
		}
	} // check
	// if
	// space
	// is
	// pressed
}

// replace selects
function replaceSelects() {
	for ( var q = 0; q < selects.length; q++) {
		if (!selects[q].replaced && selects[q].offsetWidth) {
			selects[q]._number = q;
			// create and build div structure
			var selectArea = document.createElement("div");
			var left = document.createElement("span");
			left.className = "left";
			selectArea.appendChild(left);

			var disabled = document.createElement("span");
			disabled.className = "disabled";
			selectArea.appendChild(disabled);

			selects[q]._disabled = disabled;
			var center = document.createElement("span");
			var button = document.createElement("a");
			var text = document.createTextNode(selectText);
			center.id = "mySelectText" + q;

			var stWidth = selects[q].offsetWidth;
			selectArea.style.width = stWidth + "px";
			if (selects[q].parentNode.className.indexOf("type2") != -1) {
				button.href = "javascript:showOptions(" + q + ",true)";
			} else {
				button.href = "javascript:showOptions(" + q + ",false)";
			}
			button.className = "selectButton";
			selectArea.className = "selectArea";
			selectArea.className += " " + selects[q].className;
			selectArea.id = "sarea" + q;
			center.className = "center";
			center.appendChild(text);
			selectArea.appendChild(center);
			selectArea.appendChild(button);

			// insert select div
			selects[q].parentNode.insertBefore(selectArea, selects[q]);
			// build & place options div

			var optionsDiv = document.createElement("div");
			var optionsList = document.createElement("ul");
			var optionsListHolder = document.createElement("div");

			optionsListHolder.className = "select-center";
			optionsListHolder.innerHTML = "<div class='select-center-right'></div>";
			optionsDiv.innerHTML += "<div class='select-top'><div class='select-top-left'></div><div class='select-top-right'></div></div>";

			optionsListHolder.appendChild(optionsList);
			optionsDiv.appendChild(optionsListHolder);

			selects[q]._optionsDiv = optionsDiv;
			selects[q]._options = optionsList;

			optionsDiv.style.width = stWidth + "px";
			optionsDiv._parent = selectArea;

			optionsDiv.className = "optionsDivInvisible";
			optionsDiv.id = "optionsDiv" + q;

			if (selects[q].className.length) {
				optionsDiv.className += ' drop-' + selects[q].className;
			}

			populateSelectOptions(selects[q]);
			optionsDiv.innerHTML += "<div class='select-bottom'><div class='select-bottom-left'></div><div class='select-bottom-right'></div></div>";
			document.body.appendChild(optionsDiv);
			selects[q].replaced = true;

			// too many options
			if (selects[q].options.length > maxVisibleOptions) {
				optionsDiv.className += ' optionsDivScroll';
			}

			// hide the select field
			if (selects[q].className.indexOf('default') != -1) {
				selectArea.style.display = 'none';
			} else {
				selects[q].className += " outtaHere";
			}
		}
	}
	all_selects = true;
}

// collecting select options
function populateSelectOptions(me) {
	me._options.innerHTML = "";
	for ( var w = 0; w < me.options.length; w++) {
		var optionHolder = document.createElement('li');
		var optionLink = document.createElement('a');
		var optionTxt;
		if (me.options[w].title.indexOf('image') != -1) {
			optionTxt = document.createElement('img');
			optionSpan = document.createElement('span');
			optionTxt.src = me.options[w].title;
			optionSpan = document.createTextNode(me.options[w].text);
		} else {
			optionTxt = document.createTextNode(me.options[w].text);
		}

		// hidden default option
		if (me.options[w].className.indexOf('default') != -1) {
			optionHolder.style.display = 'none'
		}

		optionLink.href = "javascript:showOptions(" + me._number
				+ "); selectMe('" + me.id + "'," + w + "," + me._number + ");";
		if (me.options[w].title.indexOf('image') != -1) {
			optionLink.appendChild(optionTxt);
			optionLink.appendChild(optionSpan);
		} else {
			optionLink.appendChild(optionTxt);
		}
		optionHolder.appendChild(optionLink);
		me._options.appendChild(optionHolder);
		// check for pre-selected items
		if (me.options[w].selected) {
			selectMe(me.id, w, me._number, true);
		}
	}
	if (me.disabled) {
		me._disabled.style.display = "block";
	} else {
		me._disabled.style.display = "none";
	}
}

// selecting me
function selectMe(selectFieldId, linkNo, selectNo, quiet) {
	selectField = selects[selectNo];
	for ( var k = 0; k < selectField.options.length; k++) {
		if (k == linkNo) {
			selectField.options[k].selected = true;
		} else {
			selectField.options[k].selected = false;
		}
	}

	// show selected option
	textVar = document.getElementById("mySelectText" + selectNo);
	var newText;
	var optionSpan;
	if (selectField.options[linkNo].title.indexOf('image') != -1) {
		newText = document.createElement('img');
		newText.src = selectField.options[linkNo].title;
		optionSpan = document.createElement('span');
		optionSpan = document.createTextNode(selectField.options[linkNo].text);
	} else {
		newText = document.createTextNode(selectField.options[linkNo].text);
	}
	if (selectField.options[linkNo].title.indexOf('image') != -1) {
		if (textVar.childNodes.length > 1)
			textVar.removeChild(textVar.childNodes[0]);
		textVar.replaceChild(newText, textVar.childNodes[0]);
		textVar.appendChild(optionSpan);
	} else {
		if (textVar.childNodes.length > 1)
			textVar.removeChild(textVar.childNodes[0]);
		textVar.replaceChild(newText, textVar.childNodes[0]);
	}
	if (!quiet && all_selects) {
		if (typeof selectField.onchange === 'function') {
			selectField.onchange();
		}
		if (window.$ && window.$.fn) {
			$(selectField).trigger('change');
		}
	}
}

// showing options
function showOptions(g) {
	_elem = document.getElementById("optionsDiv" + g);
	var divArea = document.getElementById("sarea" + g);
	if (active_select && active_select != _elem) {
		active_select.className = active_select.className.replace(
				'optionsDivVisible', ' optionsDivInvisible');
		active_select.style.height = "auto";
	}
	if (_elem.className.indexOf("optionsDivInvisible") != -1) {
		_elem.style.left = "-9999px";
		_elem.style.top = findPosY(divArea) + divArea.offsetHeight + 'px';
		_elem.className = _elem.className.replace('optionsDivInvisible', '');
		_elem.className += " optionsDivVisible";
		/*
		 * if (_elem.offsetHeight > 200) { _elem.style.height = "200px"; }
		 */
		_elem.style.left = findPosX(divArea) + 'px';

		active_select = _elem;
		if (_elem._parent.className.indexOf('selectAreaActive') < 0) {
			_elem._parent.className += ' selectAreaActive';
		}

		if (document.documentElement) {
			document.documentElement.onclick = hideSelectOptions;
		} else {
			window.onclick = hideSelectOptions;
		}
	} else if (_elem.className.indexOf("optionsDivVisible") != -1) {
		hideActiveSelectDrop();
	}

	// for mouseout
	/*
	 * _elem.timer = false; _elem.onmouseover = function() { if (this.timer)
	 * clearTimeout(this.timer); } _elem.onmouseout = function() { var _this =
	 * this; this.timer = setTimeout(function(){ _this.style.height = "auto";
	 * _this.className = _this.className.replace('optionsDivVisible',''); if
	 * (_elem.className.indexOf('optionsDivInvisible') == -1) _this.className += "
	 * optionsDivInvisible"; },200); }
	 */
}

function hideActiveSelectDrop() {
	if (active_select) {
		active_select.style.height = "auto";
		active_select.className = active_select.className.replace(
				'optionsDivVisible', '');
		active_select.className = active_select.className.replace(
				'optionsDivInvisible', '');
		active_select._parent.className = active_select._parent.className
				.replace('selectAreaActive', '')
		active_select.className += " optionsDivInvisible";
		active_select = false;
	}
}

function hideSelectOptions(e) {
	if (active_select) {
		if (!e)
			e = window.event;
		var _target = (e.target || e.srcElement);
		if (!isElementBefore(_target, 'selectArea')
				&& !isElementBefore(_target, 'optionsDiv')) {
			hideActiveSelectDrop();
			if (document.documentElement) {
				document.documentElement.onclick = function() {
				};
			} else {
				window.onclick = null;
			}
		}
	}
}

function isElementBefore(_el, _class) {
	var _parent = _el;
	do {
		_parent = _parent.parentNode;
	} while (_parent && _parent.className != null
			&& _parent.className.indexOf(_class) == -1)
	return _parent.className && _parent.className.indexOf(_class) != -1;
}

function findPosY(obj) {
	if (obj.getBoundingClientRect) {
		var scrollTop = window.pageYOffset
				|| document.documentElement.scrollTop
				|| document.body.scrollTop;
		var clientTop = document.documentElement.clientTop
				|| document.body.clientTop || 0;
		return Math.round(obj.getBoundingClientRect().top + scrollTop
				- clientTop);
	} else {
		var posTop = 0;
		while (obj.offsetParent) {
			posTop += obj.offsetTop;
			obj = obj.offsetParent;
		}
		return posTop;
	}
}

function findPosX(obj) {
	if (obj.getBoundingClientRect) {
		var scrollLeft = window.pageXOffset
				|| document.documentElement.scrollLeft
				|| document.body.scrollLeft;
		var clientLeft = document.documentElement.clientLeft
				|| document.body.clientLeft || 0;
		return Math.round(obj.getBoundingClientRect().left + scrollLeft
				- clientLeft);
	} else {
		var posLeft = 0;
		while (obj.offsetParent) {
			posLeft += obj.offsetLeft;
			obj = obj.offsetParent;
		}
		return posLeft;
	}
};

(function() {
	// html5shiv MIT @rem remysharp.com/html5-enabling-script
	// iepp v1.6.2 MIT @jon_neal iecss.com/print-protector
	/*
	 * @cc_on(function(m,c){var
	 * z="abbr|article|aside|audio|canvas|details|figcaption|figure|footer|header|hgroup|mark|meter|nav|output|progress|section|summary|time|video";function
	 * n(d){for(var a=-1;++a<o;)d.createElement(i[a])}function p(d,a){for(var
	 * e=-1,b=d.length,j,q=[];++e<b;){j=d[e];if((a=j.media||a)!="screen")q.push(p(j.imports,a),j.cssText)}return
	 * q.join("")}var g=c.createElement("div");g.innerHTML="<z>i</z>";if(g.childNodes.length!==1){var
	 * i=z.split("|"),o=i.length,s=RegExp("(^|\\s)("+z+")", "gi"),t=RegExp("<(/*)("+z+")","gi"),u=RegExp("(^|[^\\n]*?\\s)("+z+")([^\\n]*)({[\\n\\w\\W]*?})","gi"),r=c.createDocumentFragment(),k=c.documentElement;g=k.firstChild;var
	 * h=c.createElement("body"),l=c.createElement("style"),f;n(c);n(r);g.insertBefore(l,
	 * g.firstChild);l.media="print";m.attachEvent("onbeforeprint",function(){var
	 * d=-1,a=p(c.styleSheets,"all"),e=[],b;for(f=f||c.body;(b=u.exec(a))!=null;)e.push((b[1]+b[2]+b[3]).replace(s,"$1.iepp_$2")+b[4]);for(l.styleSheet.cssText=e.join("\n");++d<o;){a=c.getElementsByTagName(i[d]);e=a.length;for(b=-1;++b<e;)if(a[b].className.indexOf("iepp_")<0)a[b].className+="
	 * iepp_"+i[d]}r.appendChild(f);k.appendChild(h);h.className=f.className;h.innerHTML=f.innerHTML.replace(t,"<$1font")});m.attachEvent("onafterprint",
	 * function(){h.innerHTML="";k.removeChild(h);k.appendChild(f);l.styleSheet.cssText=""})}})(this,document);@
	 */
})()