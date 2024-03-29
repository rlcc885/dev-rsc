/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


var BrowserDetect={
    init:function(){
        this.browser=this.searchString(this.dataBrowser)||"An unknown browser";
        this.version=this.searchVersion(navigator.userAgent)||this.searchVersion(navigator.appVersion)||"an unknown version";
        this.OS=this.searchString(this.dataOS)||"an unknown OS";
    },
    searchString:function(data){
        for(var i=0;i<data.length;i++){
            var dataString=data[i].string;
            var dataProp=data[i].prop;
            this.versionSearchString=data[i].versionSearch||data[i].identity;
            if(dataString){
                if(dataString.indexOf(data[i].subString)!=-1)
                    return data[i].identity;
            }
            else if(dataProp)
                return data[i].identity;
        }
    },
    searchVersion:function(dataString){
        var index=dataString.indexOf(this.versionSearchString);
        if(index==-1)return;
        return parseFloat(dataString.substring(index+this.versionSearchString.length+1));
    },
    dataBrowser:[{
        string:navigator.userAgent,
        subString:"Chrome",
        identity:"Chrome"
    },{
        string:navigator.userAgent,
        subString:"OmniWeb",
        versionSearch:"OmniWeb/",
        identity:"OmniWeb"
    },{
        string:navigator.vendor,
        subString:"Apple",
        identity:"Safari"
    },{
        prop:window.opera,
        identity:"Opera"
    },{
        string:navigator.vendor,
        subString:"iCab",
        identity:"iCab"
    },{
        string:navigator.vendor,
        subString:"KDE",
        identity:"Konqueror"
    },{
        string:navigator.userAgent,
        subString:"Firefox",
        identity:"Firefox"
    },{
        string:navigator.vendor,
        subString:"Camino",
        identity:"Camino"
    },{
        string:navigator.userAgent,
        subString:"Netscape",
        identity:"Netscape"
    },{
        string:navigator.userAgent,
        subString:"MSIE",
        identity:"Internet Explorer",
        versionSearch:"MSIE"
    },{
        string:navigator.userAgent,
        subString:"Gecko",
        identity:"Mozilla",
        versionSearch:"rv"
    },{
        string:navigator.userAgent,
        subString:"Mozilla",
        identity:"Netscape",
        versionSearch:"Mozilla"
    }],
    dataOS:[{
        string:navigator.platform,
        subString:"Win",
        identity:"Windows"
    },{
        string:navigator.platform,
        subString:"Mac",
        identity:"Mac"
    },{
        string:navigator.platform,
        subString:"Linux",
        identity:"Linux"
    }]
};

BrowserDetect.init();

var UpdateYourBrowserInit=function(){
    var asn_resize=null;

    jQuery(document).ready(function(){
        asn_resize=function(){
            if(jQuery('#asn-warning').width()<600){
                jQuery('#asn-outofdate').hide();
            }
            else if(jQuery('#asn-warning').width()<790){
                jQuery('#asn-outofdate').show().html('!');
            }
            else{
                jQuery('#asn-outofdate').show().html('Navegador desactualizado!');
            }
        }
        if(((BrowserDetect.browser=="Internet Explorer")&&(parseInt(BrowserDetect.version)<=8))||((BrowserDetect.browser=="Chrome")&&(parseInt(BrowserDetect.version)<=15))||((BrowserDetect.browser=="Opera")&&(parseInt(BrowserDetect.version)<=9))||((BrowserDetect.browser=="Safari")&&(parseInt(BrowserDetect.version)<4))||((BrowserDetect.browser=="Firefox")&&(parseInt(BrowserDetect.version)<=10)))
        { 
            jQuery('body').remove('#asn-warning');
            jQuery('body').prepend("<div id=\"asn-warning\" style=\"position:absolute; display:none; left: 0px; border-bottom: solid 1px #DFDDCB; top:0px; margin: 0px; padding: 10px 0px; width: 100%; z-index: 99999; color: #6F6D5B; font-size: 10pt; padding: 0; background: #FFFCDF; font-family 'Trebuchet MS', Arial, Helvetica, sans-serif; text-align: left;\"><a href=\"http://www.updateyourbrowser.net/\" style=\"color: #4F4D3B; text-decoration: none; font: normal 9pt/14px 'Trebuchet MS', Arial, Helvetica; padding-right: 30px; display: block;\" target=\"_blank\"><span id=\"asn-outofdate\" style=\"display: block;  color: #fff; float: left; padding: 10px 18px 10px 8px; background: #bd695b url('http://www.updateyourbrowser.net/_static/imagens/arrow.gif') no-repeat right -2px; \">Navegador desactualizado!</span><span style=\"display: block; padding: 10px 10px; float: left;\">Usted está usando una antigua versión de <strong>"+BrowserDetect.browser+"</strong>. Le recomendamos que   <span style=\"text-decoration: underline;\">actualice su navegador</span>.</span></a> <a href=\"javascript://\" id=\"asn-close\" style=\"position: absolute; text-decoration: none; width: 14px; border: none; padding: 3px; top: 8px; right: 14px; color: #4F4D3B; height: 14px; font: normal 8pt/14px 'Trebuchet MS', Arial, Helvetica; background-image: url('http://www.updateyourbrowser.net/_static/imagens/close_03.gif');\"></a></div>");
            jQuery('#asn-warning').fadeIn(1000);
            jQuery('#asn-close').click(function(){
                jQuery('#asn-warning').fadeOut(400);
            });
            jQuery('#asn-warning a').mouseover(function(){
                jQuery(this).css('color','#8F8D7B');
            });
            jQuery('#asn-warning a').mouseout(function(){
                jQuery(this).css('color','#4F4D3B');
            });
            if((BrowserDetect.browser=="Internet Explorer")&&(parseInt(BrowserDetect.version)<=6)){}else{
                jQuery('#asn-warning').css('position','fixed');
            }
            asn_resize();
        }
    });
    jQuery(window).resize(function(){
        asn_resize()
    });
}
if(typeof jQuery=='undefined'){
    if(typeof jQuery=='function'){
        thisPageUsingOtherJSLibrary=true;
    }
    function getScript(url,success){
        var script=document.createElement('script');
        script.src=url;
        var head=document.getElementsByTagName('head')[0],done=false;
        script.onload=script.onreadystatechange=function(){
            if(!done&&(!this.readyState||this.readyState=='loaded'||this.readyState=='complete')){
                done=true;
                success();
                script.onload=script.onreadystatechange=null;
                head.removeChild(script);
            };
        
        };
    
        head.appendChild(script);
    };

    getScript('http://ajax.googleapis.com/ajax/libs/jquery/1.4.4/jquery.min.js',function(){
        if(typeof jQuery=='undefined'){}else{
            jQuery.noConflict()(function(){
                UpdateYourBrowserInit();
            });
        //        if(thisPageUsingOtherJSLibrary){}else{}
        }
    });
}else{
    UpdateYourBrowserInit();
};