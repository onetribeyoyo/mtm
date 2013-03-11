/**
 * emm-v3 JavaScript
 */
jQuery(function ($) {
	var EMM = {
		hint: "search",
		ie6: $.browser.msie && parseInt($.browser.version) == 6 && typeof window['XMLHttpRequest'] != "object",
		init: function () {
			// set up local scrolling
			$("#blog .comment, #spotlight-bottom, #bookmarks, #projects .bookmarks").localScroll();

			// reset styles
			this.style();

			// equal height
			this.normalize();

			// bind social/menu hover
			this.fade();

			// bind search
			this.search();

			// comment form functions
			this.commentform();
			
			// contact form
			this.contactform();
			
			// bind keyboard shortcuts
			//this.shortcuts();
			
			// bind code examples
			this.examples();
			
			// try to clean up ugliness in IE6
			if (EMM.ie6) {
				$("#ie6").show();
				$("body, #spotlight-bottom, #footer-wrapper, #footer .twitter .twitter-info").css({backgroundImage:"none"});
				$("img[src$=.png]").each(function () {
					var img = $(this),
					span = $("<span></span>").css({
						display:"block",
						height:img.height(),
						width:img.width(),
						paddingTop:img.css("padding-top"),
						paddingRight:img.css("padding-right"),
						paddingBottom:img.css("padding-bottom"),
						paddingLeft:img.css("padding-left"),
						marginTop:img.css("margin-top"),
						marginRight:img.css("margin-right"),
						marginBottom:img.css("margin-bottom"),
						marginLeft:img.css("margin-left"),
						position:"relative",
						top:img.css("top"),
						filter: 'progid:DXImageTransform.Microsoft.AlphaImageLoader(src="' +  this.src + '", sizingMethod="crop")'
					});
					img.hide().after(span);
				});
			}
		},
		bindkeys: function () {
			$(document).bind("keydown.emm", EMM.keydown);
		},
		commentform: function () {
			var form = $("#commentform"); 
			form.submit(function () {
				var message = $("#respond .message"),
					author = $("#author"),
					email = $("#email"),
					comment = $("#comment"),
					valid = true;

				$(".required", form[0]).removeClass("required");
				message.fadeOut();
				
				$.each([author,email,comment], function (i, el) {
					if (el.length && !$.trim(el.val())) {
						el.addClass("required");
						valid = false;
					}
				});
				
				if (!valid) {
					$.scrollTo("#respond", 500);
					message.html("Please fill-in the required fields &darr;").fadeIn();
				}
				
				return valid;
			});
		},
		contactform: function () {
			var form = $("#contactform"); 
			form.submit(function (e) {
				e.preventDefault();

				var title = $("#contact-form h2"),
					message = $("#contact .message"),
					loading = $("#contact .loading"),
					status = $("#contact .status"),
					name = $("#contact-name"),
					email = $("#contact-email"),
					subject = $("#contact-subject"),
					comment = $("#contact-message"),
					valid = true;

				$(".required", form[0]).removeClass("required");
				message.fadeOut();
				
				$.each([name, email, subject, comment], function (i, el) {
					if (el.length && !$.trim(el.val())) {
						el.addClass("required");
						valid = false;
					}
				});
				
				if (!valid) {
					$.scrollTo("#contact-form", 500);
					message.html("Please fill-in required the fields &darr;").fadeIn();
				}
				else {
					form.slideUp(function () {
						loading.show(function () {
							$.ajax({
								url: $("#contactform").attr("action"),
								data: $("#contactform").serialize(),
								type: "post",
								cache: false,
								dataType: "json",
								success: function (data) {
									if (data.result != 1) {
										title.html("Uh-oh!");
									}
									else {
										title.html("Thank You");
									}
									loading.fadeOut(function () {
										status.html(data.message).fadeIn(function () {
											if (data.result != 1) {
												status.find("a").one("click", function () {
													status.slideUp(function () {
														title.html("Contact Me");
														form.slideDown();
													});
													return false;
												});
											}
										});
									});

								},
								error: function (xhr) {
									loading.fadeOut(function () {
										status.html("<p>" + xhr.status + ': ' + xhr.statusText + "</p>").fadeIn();
									});
								}
							});
						});
					});
					
				}				
			});
		},
		examples: function () {
			var run = $("<div class='run'>RUN EXAMPLE</div>");
			$("pre[id^=example-]").hover(
				function () {
					var pre = $(this);
					pre.append(run.clone());

					$("div.run", this).show().click(function () {
						EMM.runexample(pre);
						return false;
					});

				},
				function () {
					$("div.run", this).hide().remove();
				}
			);
		},
		fade: function () {
			$("#menu a:not(.current) img").css({opacity:0}).show().hover(
				function () { //over
					$(this).stop().animate({opacity:1}, 400);
				},
				function () { //out
					$(this).stop().animate({opacity:0}, 600);
				}
			);
		},
		keydown: function (e) {
			if (e.altKey || e.originalEvent.altKey || e.ctrlKey) {
				return;
			}
			switch (e.keyCode) {
				case 49: //1
					EMM.stroke(0);
					window.location = "/";
					break;
				case 50: //2
					EMM.stroke(1);
					window.location = "/blog/";
					break;
				case 51: //3
					EMM.stroke(2);
					window.location = "/projects/";
					break;
				case 52: //4
					EMM.stroke(3);
					window.location = "/photography/";
					break;
				case 53: //5
					EMM.stroke(4);
					window.location = "/about/";
					break;
				case 54: //6
					EMM.stroke(5);
					window.location = "/contact/";
					break;
				case 67: //c
					e.preventDefault();
					if ($("#respond").length) {
						EMM.stroke(7);
						$().scrollTo("#respond", 1000, {onAfter: function () {
							var a = $("#author");
							a.length ? a.focus() : $("#comment").focus();
						}});
					}
					else {
						EMM.stroke(7, true);
					}
					break;
				case 75: //k
					var sw = $("#shortcuts-wrapper");
					sw.is(":visible") ? sw.fadeOut() : sw.fadeIn();
					break;
				case 76: //l
					window.location = "/wordpress/wp-admin/";
					break;
				case 39: //right arrow
				case 78: //n
					var next = $(".bottomnav .next a, .bottomnav .newer a");
					if (next.length) {
						EMM.stroke(9);
						window.location = next.attr("href");
					}
					else {
						EMM.stroke(9, true);
					}
					break;
				case 37: //left arrow
				case 80: //p
					var prev = $(".bottomnav .previous a, .bottomnav .older a");
					if (prev.length) {
						EMM.stroke(8);
						window.location = prev.attr("href");
					}
					else {
						EMM.stroke(8, true);
					}
					break;
				case 83: //s
					if ($("#search-form").length) {
						EMM.stroke(6);
						$().scrollTo("#search-form", 1000, {onAfter: function () {
							$("#search-query").focus();
						}});
					}
					else {
						EMM.stroke(6, true);
					}
					break;
				case 84: //t
					EMM.stroke(10);
					$().scrollTo("#top", 1000);
					break;
			}
		},
		normalize: function(el, opts) {
			var proj = $("#featured-project .content"),
				post = $("#featured-post .content");

			var h = Math.max(proj.height(), post.height());
			proj.css({height:h});
			post.css({height:h});
		},
		runexample: function (pre) {
			var id = pre.attr("id").split("-")[1];
			
			switch (id) {
				case "1":
					$("#sample").modal();
					break;

				case "2":
					$.modal($("#sample"));
					break;

				case "3":
					$("#sample").modal({overlayClose:true});
					break;

				case "4":
					$("#sample").modal({
						opacity:80,
						overlayCss: {backgroundColor:"#fff"}
					});
					break;

				case "5":
					$("#sample").modal({focus:false});
					break;

				case "6":
					$("#sample").modal({
						minHeight:400,
						minWidth: 600
					});
					break;

				case "7":
					$("#sample").modal({position: [10,10]});
					break;

				case "8":
					$("#sample").modal({position: ["50%","50%"]});
					break;

				case "9":
					var src = "http://365.ericmmartin.com/";
					$.modal('<iframe src="' + src + '" height="450" width="830" style="border:0">', {
						closeHTML:"",
						containerCss:{
							backgroundColor:"#fff", 
							borderColor:"#fff", 
							height:450, 
							padding:0, 
							width:830
						},
						overlayClose:true
					});
					break;

				case "10":
					$("#sample").modal({onOpen: function (dialog) {
						dialog.overlay.fadeIn('slow', function () {
							dialog.data.hide();
							dialog.container.fadeIn('slow', function () {
								dialog.data.slideDown('slow');	 
							});
						});
					}});
					break;

				case "11":
					$("#sample").modal({onClose: function (dialog) {
						dialog.data.fadeOut('slow', function () {
							dialog.container.hide('slow', function () {
								dialog.overlay.slideUp('slow', function () {
									$.modal.close();
								});
							});
						});
					}});
					break;
			}
		},
		search: function () {
			EMM.searchHint("blur");
			
			// bind search
			$("#search-form").bind("submit", function (e) {
				e.preventDefault();
				window.location = "/search/" + $("#search-query").val();
			});

			// bind search input hint
			$("#search-query").bind("focus blur", function (e) {
				EMM.searchHint(e.type);
			});
		},
		searchHint: function (type) {
			var query = $("#search-query");
			if (query) {
				if (type === "blur") {
					if (!query.val()) {query.val(EMM.hint);}	
				}
				else {
					if (query.val() === EMM.hint) {query.val("");}
				}
			}
		},
		shortcuts: function () {
			$("#footer .keyboard-shortcuts").show();
			var sw = $("#shortcuts-wrapper");
			sw.css({opacity:.8});

			$("#shortcuts-wrapper, #footer a.shortcuts").click(function () {
				sw.is(":visible") ? sw.fadeOut() : sw.fadeIn();
				return false;
			});
			this.bindkeys();
			$(":input:not(:submit):not(:button)")
				.livequery("focus", EMM.unbindkeys)
				.livequery("blur", EMM.bindkeys);
		},
		stroke: function (idx, notfound) {
			var sc = $("#shortcuts div").eq(idx),
				color = sc.parent().parent().css("backgroundColor");
			sc.css({backgroundColor: notfound ? "#660000" : "#666666"})
				.stop().animate({backgroundColor: color}, 1500);
		},
		style: function () {
			if ($.fontAvailable("Delicious")) {
				$("body").addClass("enriched");
			}
		},
		unbindkeys: function () {
			$(document).unbind("keydown.emm", EMM.keydown);
		}
	};

	// init
	EMM.init();
});

/* AddThis variables */
var addthis_config = {
	username: "emartin24",
	ui_delay: 100,
	services_compact: "email, favorites, twitter, delicious, facebook, digg, reddit, friendfeed, live, technorati, stumbleupon, more"
};
