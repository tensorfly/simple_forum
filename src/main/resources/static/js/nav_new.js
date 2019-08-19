 /**
  * 轮播图（图片数量无限制，缩略图列表高亮当前图片，可根据图片序号自动定位）
  * @example: http://front.chinaso365.com/autodemo/index_focus/nav_new/index.html
  * @version:        1.0
  * @param thumbs    小图wrapper
  * @param thumbList 小图列表
  * @param preBtn    轮播按钮
  * @param nextBtn   轮播按钮
  * @param atitle    大图title
  * @param thumbWidth小图宽度（每次thumb列表滚动宽度）
  * @param bigImg    大图wrapper
  * @param thumbNum  小图个数
  * @param auto      是否自动轮播
  * @param interval  自动轮播间隔时间
  **/
 (function ($) {
  function Nav(wrapper, options) {
      var defaults = {

      };
      // 初始化用户自定义选项
      this.options = $.extend(defaults, options);
      this.wrapper = $(wrapper);
      this.thumbs = $(this.options.thumbs);
      this.thumbList = $(this.options.thumbList);
      this.thumbWidth = this.options.thumbWidth;
      this.preBtn = $(this.options.preBtn);
      this.nextBtn = $(this.options.nextBtn);
      this.bigImg = $(this.options.bigImg);
      this.thumbNum = this.options.thumbNum;
      this.thumbLength = this.thumbList.children().length;
      this.piov = this.thumbNum / 2;
      this.atitle = $(this.options.atitle);
      this.enable = true;
      this.auto = this.options.auto;
      this.timer = null;
      this.interval = this.options.interval;
      this.init();
  }

  Nav.prototype = {
      // 当前图片序号
      cur: 0,
      // 当前定位序号
      loc: 0,
      init: function () {
          // 绑定事件
          this.bindEvent();
          // 设置自动轮播
          if (this.auto) {
              this.autoNav();
          }
      },
      // 图片自动轮播
      autoNav: function () {
          var _this = this;
          clearInterval(_this.timer);
          _this.timer = setInterval(function () {
              _this.nextImage();
          }, _this.interval);
      },
      // 切换到上一张图片
      preImage: function () {
          var _this = this;
          clearInterval(_this.timer);
          // 更新当前图片序号
          _this.cur--;
          // 更新当前定位序号
          _this.loc--;
          // 根据当前图片和当前定位判断导航列表自动定位
          if (this.loc > 0) {
              _this.thumbList.children().removeClass('cur')
              _this.thumbList.children().eq(_this.cur).addClass('cur');
              _this.bigImg.find('img').attr('src', _this.thumbList.children().eq(_this.cur).find('a').attr(
                  'href'))
              _this.bigImg.find('img').attr('alt', _this.thumbList.children().eq(_this.cur).find('img').attr(
                  'alt'))
              _this.atitle.html('<a target="_blank" href="' + _this.thumbList.children().eq(_this.cur).find(
                  'img').attr('url') + '">' + _this.thumbList.children().eq(_this.cur).find(
                  'img').attr('alt') + '</a><div class="num"><span class="index_num">' + (_this.cur +
                  1) + '</span>/<span class="total_num">' + _this.thumbLength + '</span></div>');
              _this.bigImg.find('a').attr('href', _this.thumbList.children().eq(_this.cur).find('img').attr(
                  'url'));
              if (_this.auto) _this.autoNav();
          } else if (this.loc == 0 && this.cur > 0) {

              this.enable = false;
              this.thumbList.animate({
                  left: '+=' + this.thumbWidth
              }, function () {

                  _this.enable = true;
                  _this.loc++;

                  _this.thumbList.children().removeClass('cur')
                  _this.thumbList.children().eq(_this.cur).addClass('cur');
                  _this.bigImg.find('img').attr('src', _this.thumbList.children().eq(_this.cur).find(
                      'a').attr('href'))
                  _this.bigImg.find('img').attr('alt', _this.thumbList.children().eq(_this.cur).find(
                      'img').attr('alt'))
                  _this.atitle.html('<a target="_blank" href="' + _this.thumbList.children().eq(
                          _this.cur).find('img').attr('url') + '">' + _this.thumbList.children()
                      .eq(_this.cur).find('img').attr('alt') +
                      '</a><div class="num"><span class="index_num">' + (_this.cur + 1) +
                      '</span>/<span class="total_num">' + _this.thumbLength +
                      '</span></div>');
                  _this.bigImg.find('a').attr('href', _this.thumbList.children().eq(_this.cur).find(
                      'img').attr('url'));
                  if (_this.auto) _this.autoNav();
              })
          } else if (this.loc == 0 && this.cur == 0) {

              _this.thumbList.children().removeClass('cur')
              _this.thumbList.children().eq(_this.cur).addClass('cur');
              _this.bigImg.find('img').attr('src', _this.thumbList.children().eq(_this.cur).find('a').attr(
                  'href'))
              _this.bigImg.find('img').attr('alt', _this.thumbList.children().eq(_this.cur).find('img').attr(
                  'alt'))
              _this.atitle.html('<a target="_blank" href="' + _this.thumbList.children().eq(_this.cur).find(
                  'img').attr('url') + '">' + _this.thumbList.children().eq(_this.cur).find(
                  'img').attr('alt') + '</a><div class="num"><span class="index_num">' + (_this.cur +
                  1) + '</span>/<span class="total_num">' + _this.thumbLength + '</span></div>');
              _this.bigImg.find('a').attr('href', _this.thumbList.children().eq(_this.cur).find('img').attr(
                  'url'));
              if (_this.auto) _this.autoNav();
          } else {
              _this.enable = false;
              _this.loc = _this.thumbNum - 1;
              _this.cur = _this.thumbLength - 1;
              this.thumbList.animate({
                  left: -this.thumbWidth * (this.thumbLength - this.thumbNum)
              }, function () {

                  _this.enable = true;

                  _this.thumbList.children().removeClass('cur')
                  _this.thumbList.children().eq(_this.cur).addClass('cur');
                  _this.bigImg.find('img').attr('src', _this.thumbList.children().eq(_this.cur).find(
                      'a').attr('href'))
                  _this.bigImg.find('img').attr('alt', _this.thumbList.children().eq(_this.cur).find(
                      'img').attr('alt'))
                  _this.atitle.html('<a target="_blank" href="' + _this.thumbList.children().eq(
                          _this.cur).find('img').attr('url') + '">' + _this.thumbList.children()
                      .eq(_this.cur).find('img').attr('alt') +
                      '</a><div class="num"><span class="index_num">' + (_this.cur + 1) +
                      '</span>/<span class="total_num">' + _this.thumbLength +
                      '</span></div>');
                  _this.bigImg.find('a').attr('href', _this.thumbList.children().eq(_this.cur).find(
                      'img').attr('url'));
                  if (_this.auto) _this.autoNav();
              })
          }


      },
      // 切换到下一张图片
      nextImage: function () {
          var _this = this;
          clearInterval(_this.timer);
          // 更新当前图片序号
          this.cur++;
          // 更新当前定位序号
          this.loc++;
          // 根据当前图片和当前定位判断导航列表自动定位
          if (this.loc < this.thumbNum - 1) {
              _this.thumbList.children().removeClass('cur')
              _this.thumbList.children().eq(_this.cur).addClass('cur');
              _this.bigImg.find('img').attr('src', _this.thumbList.children().eq(_this.cur).find('a').attr(
                  'href'))
              _this.bigImg.find('img').attr('alt', _this.thumbList.children().eq(_this.cur).find('img').attr(
                  'alt'))
              _this.atitle.html('<a target="_blank" href="' + _this.thumbList.children().eq(_this.cur).find(
                  'img').attr('url') + '">' + _this.thumbList.children().eq(_this.cur).find(
                  'img').attr('alt') + '</a><div class="num"><span class="index_num">' + (_this.cur +
                  1) + '</span>/<span class="total_num">' + _this.thumbLength + '</span></div>');
              _this.bigImg.find('a').attr('href', _this.thumbList.children().eq(_this.cur).find('img').attr(
                  'url'));
              if (_this.auto) _this.autoNav();
          } else if (this.loc == this.thumbNum - 1 && this.cur < this.thumbLength - 1) {
              this.enable = false;
              this.thumbList.animate({
                  'left': '-=' + this.thumbWidth
              }, function () {

                  _this.enable = true;

                  _this.loc--;
                  _this.thumbList.children().removeClass('cur')
                  _this.thumbList.children().eq(_this.cur).addClass('cur');
                  _this.bigImg.find('img').attr('src', _this.thumbList.children().eq(_this.cur).find(
                      'a').attr('href'))
                  _this.bigImg.find('img').attr('alt', _this.thumbList.children().eq(_this.cur).find(
                      'img').attr('alt'))
                  _this.atitle.html('<a target="_blank" href="' + _this.thumbList.children().eq(
                          _this.cur).find('img').attr('url') + '">' + _this.thumbList.children()
                      .eq(_this.cur).find('img').attr('alt') +
                      '</a><div class="num"><span class="index_num">' + (_this.cur + 1) +
                      '</span>/<span class="total_num">' + _this.thumbLength +
                      '</span></div>');
                  _this.bigImg.find('a').attr('href', _this.thumbList.children().eq(_this.cur).find(
                      'img').attr('url'));
                  if (_this.auto) _this.autoNav();
              })

          } else if (this.loc == this.thumbNum - 1 && this.cur == this.thumbLength - 1) {

              _this.thumbList.children().removeClass('cur')
              _this.thumbList.children().eq(_this.cur).addClass('cur');
              _this.bigImg.find('img').attr('src', _this.thumbList.children().eq(_this.cur).find('a').attr(
                  'href'))
              _this.bigImg.find('img').attr('alt', _this.thumbList.children().eq(_this.cur).find('img').attr(
                  'alt'))
              _this.atitle.html('<a target="_blank" href="' + _this.thumbList.children().eq(_this.cur).find(
                  'img').attr('url') + '">' + _this.thumbList.children().eq(_this.cur).find(
                  'img').attr('alt') + '</a><div class="num"><span class="index_num">' + (_this.cur +
                  1) + '</span>/<span class="total_num">' + _this.thumbLength + '</span></div>');
              _this.bigImg.find('a').attr('href', _this.thumbList.children().eq(_this.cur).find('img').attr(
                  'url'));
              if (_this.auto) _this.autoNav();
          } else {
              this.loc = 0;
              this.cur = 0;
              this.enable = false;
              this.thumbList.animate({
                  'left': 0
              }, function () {

                  _this.enable = true;

                  _this.thumbList.children().removeClass('cur')
                  _this.thumbList.children().eq(_this.cur).addClass('cur');
                  _this.bigImg.find('img').attr('src', _this.thumbList.children().eq(_this.cur).find(
                      'a').attr('href'))
                  _this.bigImg.find('img').attr('alt', _this.thumbList.children().eq(_this.cur).find(
                      'img').attr('alt'))
                  _this.atitle.html('<a target="_blank" href="' + _this.thumbList.children().eq(
                          _this.cur).find('img').attr('url') + '">' + _this.thumbList.children()
                      .eq(_this.cur).find('img').attr('alt') +
                      '</a><div class="num"><span class="index_num">' + (_this.cur + 1) +
                      '</span>/<span class="total_num">' + _this.thumbLength +
                      '</span></div>');
                  _this.bigImg.find('a').attr('href', _this.thumbList.children().eq(_this.cur).find(
                      'img').attr('url'));
                  if (_this.auto) _this.autoNav();
              })
          }

      },
      // 绑定事件
      bindEvent: function () {
          var _this = this;
          // 切换上一张图片
          this.preBtn.click(function () {
              if (_this.enable)
                  _this.preImage();
          });
          // 切换下一张图片
          this.nextBtn.click(function () {
              if (_this.enable)

                  _this.nextImage();
          });
          // 鼠标在大图上浮动停止轮播
          _this.wrapper.mouseover(function () {
              $('.ad-image-wrapper').addClass('ad-mouseover');
              $('.ad-image-wrapper').removeClass('ad-mouseout');
              if (_this.auto)
                  clearInterval(_this.timer);
          });
          _this.wrapper.mouseout(function () {
              $('.ad-image-wrapper').addClass('ad-mouseout');
              $('.ad-image-wrapper').removeClass('ad-mouseover');
              if (_this.auto)
                  _this.autoNav();
          });
          // 鼠标在导航小图上浮动停止轮播
          _this.thumbs.find('a').mouseover(function () {
              $('.ad-image-wrapper').addClass('ad-mouseover');
              $('.ad-image-wrapper').removeClass('ad-mouseout');
              if (_this.auto)
                  clearInterval(_this.timer);
          });
          _this.thumbs.find('a').mouseout(function () {
              $('.ad-image-wrapper').addClass('ad-mouseout');
              $('.ad-image-wrapper').removeClass('ad-mouseover');
              if (_this.auto)
                  _this.autoNav();
          });
          // 点击小图自动切换及定位
          _this.thumbList.find('a').click(function (e) {
              if (_this.enable) {
                  var src = $(this).attr('href');

                  var oldCur = _this.cur;
                  var oldLoc = _this.loc;
                  _this.cur = $(this).parent().index();
                  _this.loc = _this.cur - oldCur + oldLoc;
                  if (_this.cur < oldCur) {
                      _this.loc++;
                      _this.cur++;
                      _this.preImage();
                  } else /*if (_this.cur > oldCur)*/ {
                      _this.loc--;
                      _this.cur--;
                      _this.nextImage();
                  }
              }

              if (_this.auto)
                  clearInterval(_this.timer);

              /*if (_this.loc == 0 && _this.cur > 0) {

                 _this.cur++;
                 _this.preImage();
             } else {
                 _this.loc--;
                 _this.cur--;
                 _this.nextImage();
             } */
              /*else if (_this.loc == 0 && _this.cur == 0) {
                                      _this.thumbList.children().removeClass('cur')
                                      _this.thumbList.children().eq(_this.cur).addClass('cur');
                                      _this.bigImg.find('img').attr('src',_this.thumbList.children().eq(_this.cur).find('a').attr('href'))
                                      _this.atitle.html('<a target="_blank" href="'+_this.thumbList.children().eq(_this.cur).find('img').attr('url')+'">'+_this.thumbList.children().eq(_this.cur).find('img').attr('alt')+'</a>');
                                      _this.bigImg.find('a').attr('href', _this.thumbList.children().eq(_this.cur).find('img').attr('url'));
                              }
                            */
              return false;
          });
      }

  }

  window.Nav = Nav;
})(jQuery);

$(document).ready(function () {
  var $warp = $(".ad-gallery"),
      $tab = $(".ad-image-wrapper");

  $(function () {
      $warp.mouseover(function () {
          $tab.removeClass("mouseout").addClass("mouseover");
      });
      $warp.mouseout(function () {
          $tab.removeClass("mouseover").addClass("mouseout");
      });
  });
});
