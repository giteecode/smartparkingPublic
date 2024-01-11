/^http(s*):\/\//.test(location.href) || alert('请先部署到 localhost 下再访问');

var objOkTab = "";
layui.use(["element", "layer", "okUtils", "okTab", "okLayer"], function () {
    var okUtils = layui.okUtils;
    var $ = layui.jquery;
    var layer = layui.layer;
    var okLayer = layui.okLayer;
    //获取菜单数据
    var okTab = layui.okTab({
        url: "sys/menu/getByUser",
        openTabNum: 30, // 允许同时选项卡的个数
        parseData: function (data) { // 如果返回的结果和navs.json中的数据结构一致可省略这个方法
            return data;
        }
    });
    objOkTab = okTab;

    /**
     * 左侧导航渲染完成之后的操作
     * 后端菜逼-》render=》navBar 初始化左侧菜单
     */
    okTab.render(function () {
    });

    /**
     * 添加新窗口
     */
    $("body").on("click", "#navBar .layui-nav-item a", function () {
        // 如果不存在子级
        if ($(this).siblings().length == 0) {
            okTab.tabAdd($(this));
        }
        // 关闭其他展开的二级标签
        $(this).parent("li").siblings().removeClass("layui-nav-itemed");
        if (!$(this).attr('lay-id')) {
            var topLevelEle = $(this).parents("li.layui-nav-item");
            var childs = $("#navBar > li > dl.layui-nav-child").not(topLevelEle.children("dl.layui-nav-child"));
            childs.removeAttr('style');
        }
    });
     /**
     * 个人设置
     */
    $("body").on("click", "#userInfo a", function () {
        var url = $(this).attr("data-url");
        if(url!=undefined){
            var title = $(this).html();
            userInfo(url,title);
        }else{
            okLayer.confirm("确定要退出吗？", function (index) {
                window.location = "/sys/logout";
            });
        }
    });

    /**
     * 左侧菜单展开动画
     */
    $("#navBar").on('click', '.layui-nav-item a', function () {
        if (!$(this).attr('lay-id')) {
            var superEle = $(this).parent();
            var ele = $(this).next('.layui-nav-child');
            var height = ele.height();
            ele.css({'display': 'block'});

            if (superEle.is('.layui-nav-itemed')) {//是否是展开状态
                ele.height(0);
                ele.animate({
                    height: height + 'px'
                }, function () {
                    ele.css({
                        height: "auto"
                    });
                    //ele.removeAttr('style');
                });
            } else {
                ele.animate({
                    height: 0
                }, function () {
                    ele.removeAttr('style');
                });
            }
        }
    });

    /**
     * 左边菜单显隐功能
     */
    $(".ok-menu").click(function () {
        $(".layui-layout-admin").toggleClass("ok-left-hide");
        $(this).find('i').toggleClass("ok-menu-hide");
        localStorage.setItem("isResize", false);
        setTimeout(function () {
            localStorage.setItem("isResize", true);
        }, 1200);
    });

    /**
     * 移动端的处理事件
     */
    $("body").on("click", ".layui-layout-admin .ok-left a[data-url],.ok-make", function () {
        if ($(".layui-layout-admin").hasClass("ok-left-hide")) {
            $(".layui-layout-admin").removeClass("ok-left-hide");
            $(".ok-menu").find('i').removeClass("ok-menu-hide");
        }
    });

    /**
     * tab左右移动
     */
    $("body").on("click", ".okNavMove", function () {
        var moveId = $(this).attr("data-id");
        var that = this;
        okTab.navMove(moveId, that);
    });

    /**
     * 刷新当前tab页
     */
    $("body").on("click", ".ok-refresh", function () {
        okTab.refresh(this);
    });

    /**
     * 关闭tab页
     */
    $("body").on("click", "#tabAction a", function () {
        var num = $(this).attr('data-num');
        okTab.tabClose(num);
    });

    /**
     * 全屏/退出全屏
     */
    $("body").on("keydown", function (event) {
        event = event || window.event || arguments.callee.caller.arguments[0];
        // 按 Esc
        if (event && event.keyCode == 27) {
            console.log("Esc");
            $("#fullScreen").children("i").eq(0).removeClass("okicon-screen-restore");
        }
        // 按 F11
        if (event && event.keyCode == 122) {
            $("#fullScreen").children("i").eq(0).addClass("okicon-screen-restore");
        }
    });

    $("body").on("click", "#fullScreen", function () {
        if ($(this).children("i").hasClass("okicon-screen-restore")) {
            screenFun(2).then(function () {
                $(this).children("i").eq(0).removeClass("okicon-screen-restore");
            });
        } else {
            screenFun(1).then(function () {
                $(this).children("i").eq(0).addClass("okicon-screen-restore");
            });
        }
    });

    /**
     * 全屏和退出全屏的方法
     * @param num 1代表全屏 2代表退出全屏
     * @returns {Promise}
     */
    function screenFun(num) {
        num = num || 1;
        num = num * 1;
        var docElm = document.documentElement;

        switch (num) {
            case 1:
                if (docElm.requestFullscreen) {
                    docElm.requestFullscreen();
                } else if (docElm.mozRequestFullScreen) {
                    docElm.mozRequestFullScreen();
                } else if (docElm.webkitRequestFullScreen) {
                    docElm.webkitRequestFullScreen();
                } else if (docElm.msRequestFullscreen) {
                    docElm.msRequestFullscreen();
                }
                break;
            case 2:
                if (document.exitFullscreen) {
                    document.exitFullscreen();
                } else if (document.mozCancelFullScreen) {
                    document.mozCancelFullScreen();
                } else if (document.webkitCancelFullScreen) {
                    document.webkitCancelFullScreen();
                } else if (document.msExitFullscreen) {
                    document.msExitFullscreen();
                }
                break;
        }

        return new Promise(function (res, rej) {
            res("返回值");
        });
    }

    /**
     * 系统公告
     */
    $(document).on("click", "#notice", noticeFun);
    !function () {
        var notice = sessionStorage.getItem("notice");
        if (notice != "true") {
            //noticeFun();
        }
    }();

    function noticeFun() {
        var srcWidth = okUtils.getBodyWidth();
        layer.open({
            type: 0, title: "系统公告", btn: "我知道啦", btnAlign: 'c', content: getContent(),
            yes: function (index) {
                if (srcWidth > 800) {
                    layer.tips('公告跑到这里去啦', '#notice', {
                        tips: [1, '#000'],
                        time: 2000
                    });
                }
                sessionStorage.setItem("notice", "true");
                layer.close(index);
            },
            cancel: function (index) {
                if (srcWidth > 800) {
                    layer.tips('公告跑到这里去啦', '#notice', {
                        tips: [1, '#000'],
                        time: 2000
                    });
                }
            }
        });
    }

    function getContent() {
        let dateStr = okUtils.dateFormat(new Date(), "yyyy-MM-dd");
        let content = "";
        if (dateStr == "2019-12-30" || dateStr == "2019-12-31" || dateStr == "2019-01-01") {
            content = "元旦，一年之始也。元，初，始也；旦，太阳微露地平一线，是为一日之始。值此新年之际，祝诸君快乐安康，如意吉祥！<br/>" +
                "爪哇图床 祝您元旦节快乐！(^し^)";
        } else if (dateStr == "2020-02-04" || dateStr == "2020-02-05" || dateStr == "2020-02-06" || dateStr == "2020-02-07" || dateStr == "2020-02-08" || dateStr == "2020-02-09" || dateStr == "2020-02-10") {
            content = "鞭炮声声迎新年，妙联横生贴门前。<br/>" +
                "笑声处处传入耳，美味佳肴上餐桌。<br/>" +
                "谈天论地成一片，灯光通明照残夜。<br/>" +
                "稚童新衣相夸耀，旧去新来气象清。<br/>" +
                "爪哇图床 祝您春节快乐！(^し^)";
        } else if (dateStr == "2020-04-05" || dateStr == "2020-04-06" || dateStr == "2020-04-07") {
            content = "清明时节雨纷纷，路上行人欲断魂。<br/>" +
                "借问酒家何处有，牧童遥指杏花村。<br/>" +
                "爪哇图床 祝您清明节快乐！(^し^)";
        } else if (dateStr == "2020-05-01" || dateStr == "2020-05-02" || dateStr == "2020-05-03" || dateStr == "2020-05-04") {
            content = "锄禾日当午，汗滴禾下土。<br/>" +
                "谁知盘中餐，粒粒皆辛苦。<br/>" +
                "爪哇图床 祝您劳动节快乐！(^し^)";
        } else if (dateStr == "2020-06-07" || dateStr == "2020-06-08" || dateStr == "2020-06-09") {
            content = "少年佳节倍多情，老去谁知感慨生。<br>" +
                "不效艾符趋习俗，但祈蒲酒话升平。<br>" +
                "鬓丝日日添白头，榴锦年年照眼明。<br>" +
                "千载贤愚同瞬息，几人湮没几垂名。<br>" +
                "爪哇图床 祝您端午节安康！(^し^)";
        } else if (dateStr == "2020-09-13" || dateStr == "2019-09-14" || dateStr == "2020-09-15") {
            content = "中庭地白树栖鸦，冷露无声湿桂花。<br/>" +
                "今夜月明人尽望，不知秋思落谁家。<br/>" +
                "爪哇图床 祝您中秋节快乐！(^し^)";
        } else if (dateStr == "2020-09-18") {
            content = "铭记九一八，让历史的风云鞭策我们奋进的脚步，让先烈的忠魂聆听我们自强的怒吼;<br/>" +
                "不忘九一八，让未来的发展见证华夏的崛起，让世界的目光聚焦中国的奇迹。<br/>" +
                "勿忘国耻，爱我中华，吾辈当自强！";
        } else if (dateStr == "2020-10-01" || dateStr == "2020-10-02" || dateStr == "2020-10-03" || dateStr == "2020-10-04" || dateStr == "2020-10-05" || dateStr == "2020-10-06" || dateStr == "2020-10-07") {
            content = "龙跃甲子，鸽翱晴空，凤舞九天。<br/>" +
                "昔关河黍离，列强逐鹿；神州放眼，一鹤冲天。<br/>" +
                "重振社稷，举中流誓，今看东方盛世还。<br/>" +
                "黄河血，慨仁人志士，魂祭新篇。<br/>" +
                "华夏意气峥嵘，傲五湖四海锦绣满。<br/>" +
                "壮三山五岳，叠古风姿；九经三史，彰现华韵。<br/>" +
                "豪客泼墨，贤士铺卷，放歌九州富丽妍。<br/>" +
                "泰山脊，领风骚环宇，有谁堪比？<br/>" +
                "爪哇图床 祝您国庆节快乐！<br/>祝福伟大的祖国，越来越强大。<br/>祖国强大的祖国，一直屹立于世界东方！(^し^)";
        } else {
            content = "PayCloud-支付上线啦(^し^)<br/>" +
                "在此郑重承诺该项目<span style='color:#5cb85c'>商业用户永久免费更新</span><br/>" +
                "若有更好的建议欢迎<span id='noticeQQ'>加入微信群</span>一起聊";
        }
        return content;
    }

    /**
     * 捐赠作者
     */
    $(".layui-footer button.donate").click(function () {
        layer.tab({
            area: ["330px", "350px"],
            tab: [{
                title: "支付宝",
                content: "<img src='images/zfb.jpg' width='200' height='300' style='margin-left: 60px'>"
            }, {
                title: "微信",
                content: "<img src='images/wx.jpg' width='200' height='300' style='margin-left: 60px'>"
            }]
        });
    });

    /**
     * QQ群交流
     */
    $("body").on("click", ".layui-footer button.communication,#noticeQQ", function () {
        layer.tab({
            area: ["330px", "350px"],
            tab: [{
                title: "微信群",
                content: "<img src='images/wechat.jpg' width='300' height='300' style='margin-left: 20px'>"
            }]
        });
    });

    /**
     * 锁定账户
     */
    $("#lock").click(function () {
        okLayer.confirm("确定要锁定账户吗？", function (index) {
            layer.close(index);
            $(".yy").show();
            layer.prompt({
                btn: ['确定'],
                title: '输入密码解锁(123456)',
                closeBtn: 0,
                formType: 1
            }, function (value, index, elem) {
                if (value == "123456") {
                    layer.close(index);
                    $(".yy").hide();
                } else {
                    layer.msg('密码错误', {anim: 6, time: 1000});
                }
            });
        });
    });
    function userInfo(url,title){
        okUtils.dialogOpen({
            title: title,
            url: url,
            scroll : true,
            width: '40%',
            height: '60%',
            success : function(dialog) {
                dialog.vm.load();
            },
            yes : function(dialog) {
                dialog.vm.acceptClick();
            }
        });
    }

    console.log("       _                     _       _       \n" +
        "      | |                   | |     (_)      \n" +
        "  ___ | |  _ _____ _____  __| |____  _ ____  \n" +
        " / _ \\| |_/ |_____|____ |/ _  |    \\| |  _ \\ \n" +
        "| |_| |  _ (      / ___ ( (_| | | | | | | | |\n" +
        " \\___/|_| \\_)     \\_____|\\____|_|_|_|_|_| |_|\n" +
        "                                             \n" +
        "版本：v1.0.0\n" +
        "作者：小柒2012\n" +
        "邮箱：admin@52itstyle.vip\n" +
        "企鹅：345849402\n" +
        "描述：一个很赞的云支付项目！");
});
