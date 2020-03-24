//加载进度条

var bar=$(".my-load-progress-bar");
var myText=$(".my-load-text");
var count=0;
function myProgressLoad(counts){
    if (counts<100) {
        if (counts==99){
            bar.css({"width":'99%'});
            myText.text(counts);
        }else {
            counts+=Math.floor(Math.random()*25+10);
            bar.css({"width":+counts+'%'});
            if (counts<100){
                myText.text(counts);
                console.log(counts);
            }else {
                myText.text("99%");
            }

            setTimeout("myProgressLoad("+counts+")",Math.random()*300);
        }
    }else {
        bar.css({"width":'99%'});
        myText.text("99%");
    }
}

document.onreadystatechange = function () {
    if (document.readyState === "interactive") {
        myProgressLoad(9);
    } else if(document.readyState === "complete") {
        bar.css({"width":'100%'});
        myText.text("100%");
        console.log(bar.width()+"===="+myText.text());
        $(".my-load").css("display","none");
        $(".head-nav").css({"display":"block"});
        $(".m-content").css({"display":"block"});
        //index
        $(".my-boll").css("display","none");
        $(".index-content").css({"display":"block"});
        //login
        $(".login-box").css({"display":"block"});
    }
};
