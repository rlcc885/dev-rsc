/*
* jALDropDown version 1.7.3
* Copyright 2011, Assign Labs
* licensed under the GPL licenses.
* Released under the GPL Licenses.
* Date: Web Mar 09 17:30:00
*/
$.fn.jALDropdown=function(d){try{var h={containerId:$(this).attr("id"),json_Extend:"",customitem:"item-value",customhover:"item-value-active",defaultSettings:{DataTextField:"text",DataValueField:"value",EventHandlers:{OnChange:function(l,k){return $()}},cssStyle:{itemContainerCss:"item-container"}},type:"jaldropdown"};var j=$.extend({},{Structure:{}},d);var b=null;var e=$(this).attr("id");var f={container_on_KeyDown:function(o){var n=0;try{$("#Btn"+e).attr("OnList","false");$("#PnlList"+e).attr("OnList","false");if(o.which>64&&o.which<91||o.which>96&&o.which<123){var r=String.fromCharCode(o.which);var l=$("#DlList"+e+" dt[itemtext^='"+r.toUpperCase()+"'],#DlList"+e+" dt[itemtext^='"+r.toLowerCase()+"']");for(i=0;i<l.length;i++){if($(l[i]).hasClass("ui-state-hover")){if(i==l.length-1){if($("#PnlListWrap"+e).hasClass("ui-helper-hidden-accessible")==false){$(l[0]).mouseover()}else{$(l[0]).mouseover().click()}}else{if($("#PnlListWrap"+e).hasClass("ui-helper-hidden-accessible")==false){$(l[i+1]).mouseover()}else{$(l[i+1]).mouseover().click()}}if($(l[i]).offset()){n=($(l[i]).offset().top-$("#PnlListWrap"+e).offset().top);if(n<0){$("#PnlListWrap"+e).attr("scrollTop",($("#PnlListWrap"+e).attr("scrollTop")*1)-Math.abs(n))}}if($(l[i]).offset()){n=($("#PnlListWrap"+e).height()-52)-($(l[i]).offset().top-$("#PnlListWrap"+e).offset().top);if(n<0){$("#PnlListWrap"+e).attr("scrollTop",($("#PnlListWrap"+e).attr("scrollTop")*1)+Math.abs(n))}}return}}if(l.length>0){if($("#PnlListWrap"+e).hasClass("ui-helper-hidden-accessible")==false){$(l[0]).mouseover()}else{$(l[0]).mouseover().click()}if($(l[0]).offset()){n=($(l[0]).offset().top-$("#PnlListWrap"+e).offset().top);if(n<0){$("#PnlListWrap"+e).attr("scrollTop",($("#PnlListWrap"+e).attr("scrollTop")*1)-Math.abs(n))}}if($(l[0]).offset()){n=($("#PnlListWrap"+e).height()-52)-($(l[0]).offset().top-$("#PnlListWrap"+e).offset().top);if(n<0){$("#PnlListWrap"+e).attr("scrollTop",($("#PnlListWrap"+e).attr("scrollTop")*1)+Math.abs(n))}}return}}var q=$("#PnlListWrap"+h.containerId).find("dt[hover=1]");var k=false;var m=$("#PnlListWrap"+h.containerId).find("dt");if($("#PnlListWrap"+e).hasClass("ui-helper-hidden-accessible")){q=m.filter("dt[itemval='"+f.getValueForAttribute($(this).attr("itemval"))+"']")}else{if(q.length===0){q=m.filter("dt[itemval='"+f.getValueForAttribute($(this).attr("itemval"))+"']");if(q.length===0){k=true;q=$("dt:eq(0)",m)}else{q.mouseover()}}}switch(o.which){case 116:return true;case 38:if(m.index(q)===0){return false}if($("#PnlListWrap"+e).hasClass("ui-helper-hidden-accessible")==false){q.mouseout();q=q.prev().mouseover();if(q.prev().offset()){n=(q.prev().offset().top-$("#PnlListWrap"+e).offset().top);if(n<0){$("#PnlListWrap"+e).attr("scrollTop",($("#PnlListWrap"+e).attr("scrollTop")*1)-Math.abs(n))}}}else{q.prev().click();if(q.prev().offset()){n=(q.prev().offset().top-$("#PnlListWrap"+e).offset().top);if(n<0){$("#PnlListWrap"+e).attr("scrollTop",($("#PnlListWrap"+e).attr("scrollTop")*1)-Math.abs(n))}}return false}break;case 40:if(m.index(q)==m.length-1){return false}if($("#PnlListWrap"+e).hasClass("ui-helper-hidden-accessible")==false){q.mouseout();q=q.next().mouseover();if(q.next().offset()){n=($("#PnlListWrap"+e).height()-52)-(q.next().offset().top-$("#PnlListWrap"+e).offset().top);if(n<0){$("#PnlListWrap"+e).attr("scrollTop",($("#PnlListWrap"+e).attr("scrollTop")*1)+Math.abs(n))}}}else{q.next().click();if(q.next().offset()){n=($("#PnlListWrap"+e).height()-52)-(q.next().offset().top-$("#PnlListWrap"+e).offset().top);if(n<0){$("#PnlListWrap"+e).attr("scrollTop",($("#PnlListWrap"+e).attr("scrollTop")*1)+Math.abs(n))}}return false}break;case 13:if($("#PnlListWrap"+e).hasClass("ui-helper-hidden-accessible")==false){q.click();return false}else{return true}break;case 9:if($("#PnlListWrap"+e).hasClass("ui-helper-hidden-accessible")==false){q.click();return true}else{return true}break;default:return false}if($("#PnlListWrap"+e).hasClass("ui-helper-hidden-accessible")){q.click();return false}return false}catch(p){$($("#"+e)).trigger("OnError",[{Message:"Error in container_on_KeyDown"}])}},showPanel:function(k){$("#PnlListWrap"+k).removeClass("ui-helper-hidden-accessible");if($("#DlList"+k).height()<=$("#PnlListWrap"+k).height()){$("#PnlListWrap"+k).css("height","auto")}else{$("#PnlListWrap"+k).css("height",$("#"+k).attr("defaultheight"))}},hidePanel:function(k){$("#PnlListWrap"+k).css("top","");$("#PnlListWrap"+k).css("left","");$("#PnlListWrap"+k).addClass("ui-helper-hidden-accessible")},togglePanel:function(k){if($("#PnlListWrap"+k).hasClass("ui-helper-hidden-accessible")==false){f.hidePanel(k)}else{f.showPanel(k)}},getValueForAttribute:function(l){if(l!=undefined&&l!=null){var k=new RegExp("'","g");l=l.replace(k,"\\'")}else{l=null}return l},showList:function(){var k=$(this).attr("id").substr(3);try{if(!$("#"+k).attr("disabled")){$("#PnlCntnr"+k).addClass("ui-state-active");if($("#PnlListWrap"+k).hasClass("ui-helper-hidden-accessible")==false){f.hidePanel(k);$("#PnlCntnr"+k).removeClass("ui-state-active");return}else{$("#Btn"+k).attr("OnList","true");var l=$("#PnlCntnr"+k);$("#PnlCntnr"+k).addClass("ui-state-active");f.togglePanel(k);$("#Btn"+k).focus();$("#PnlListWrap"+k).position({of:$("#"+k),my:"left top",at:"left bottom",offset:"0 0",collision:"none"})}if($("#PnlListWrap"+k).hasClass("ui-helper-hidden-accessible")==false){$("#PnlList"+k).find("dt").filter("[itemval='"+f.getValueForAttribute($("#"+k).attr("itemval"))+"']").mouseover();$("#PnlList"+k).attr("OnList","false")}}}catch(m){$($("#"+k)).trigger("OnError",[{Message:"Error in showList Method"}])}},pnlFocus:function(){var k=$(this).attr("id").substr(3);try{if(!$("#"+k).attr("disabled")){if($("#PnlListWrap"+k).hasClass("ui-helper-hidden-accessible")){$("#PnlCntnr"+k).addClass("ui-state-active")}}}catch(l){$($("#"+k)).trigger("OnError",[{Message:"Error in pnlFocus Method"}])}},hideList:function(){var k=$(this).attr("id").substr(3);try{if($("#PnlList"+k).attr("OnList")=="false"&&$("#TxtDD"+k).attr("onOver")=="false"){$("#Btn"+k).attr("OnList","false")}if($("#Btn"+k).attr("OnList")=="false"){f.hidePanel(k);$("#PnlCntnr"+k).removeClass("ui-state-active")}}catch(l){$($("#"+k)).trigger("OnError",[{Message:"Error in hideList Method"}])}},divMouseOver:function(l){var k=$(this).attr("id").substr(11);try{if($(l.target).context.nodeName=="DIV"&&($(l.target).attr("type")=="ITEMDIV")){$("#PnlList"+k).attr("OnList","true")}else{if($(l.target).context.nodeName=="DT"&&$(l.target).attr("dtCurrent")=="hide"){$(l.target).parent().find('dt[dtCurrent="hide"]').removeClass("ui-state-hover").removeClass(h.customhover).addClass(h.customitem).removeAttr("hover");$(l.target).removeClass(h.customitem).addClass("ui-state-hover").addClass(h.customhover).attr("hover","1");$("#PnlList"+k).attr("OnList","true")}else{if($(l.target).attr("dtCurrent")!="hide"&&$(l.target).attr("id")!="Btn"+k){$("#PnlList"+k).find('dt[dtCurrent="hide"]').removeClass("ui-state-hover").removeClass(h.customhover).addClass(h.customitem).removeAttr("hover");$(l.target).parents('DT[dtCurrent="hide"]').removeClass(h.customitem).addClass("ui-state-hover").addClass(h.customhover).attr("hover","1");$("#PnlList"+k).attr("OnList","true")}}}}catch(m){$($("#"+k)).trigger("OnError",[{Message:"Error in divMouseOver Method"}])}},divMouseLeave:function(m){var k=$(this).attr("id").substr(11);var l=$("#PnlList"+k);try{if($(m.target).context.nodeName=="DIV"&&$(m.target).attr("type")=="ITEMDIVWRAP"){l.attr("OnList","false");$("#Btn"+k).focus()}else{l.attr("OnList","false")}}catch(n){$($("#"+k)).trigger("OnError",[{Message:"Error in divMouseLeave Method"}])}},container_on_Click:function(n){var l=$(this).attr("id").substr(11);var k=$("#PnlCntnr"+l);var m=$("#"+l);try{var p=p||n;var o;if($(p.target).context.nodeName=="DT"||$($(p.target).parents('dt["dtCurrent"=="hide"]')).length>0){if($(p.target).context.nodeName!="DIV"&&$(p.target).attr("dtCurrent")==="hide"){current=$(p.target);o=$(this).find("dt").index(current);if(current.attr("itemval")!==$("#"+l).attr("itemval")){f.setItemValue(l,current.attr("itemval"),current.html());m.trigger("OnChange",[{selectedItem:current,selectedValue:$(p.target).attr("itemval"),selectedText:$(p.target).html()}])}current.parent().parent().parent().css("top","");current.parent().parent().parent().css("left","");current.parent().parent().parent().addClass("ui-helper-hidden-accessible");$("#Btn"+l).focus()}else{if($(p.target).attr("dtCurrent")!="hide"){current=$(p.target).parents('DT[dtCurrent="hide"]');o=$(this).find("dt").index(current);f.setItemValue(l,current.attr("itemval"),current.html());m.trigger("OnChange",[{selectedItem:current,selectedValue:current.attr("itemval"),selectedText:current.html()}]);current.parent().parent().parent().css("top","");current.parent().parent().parent().css("left","");current.parent().parent().parent().addClass("ui-helper-hidden-accessible");$("#Btn"+l).focus()}else{}}}}catch(q){$($("#"+l)).trigger("OnError",[{Message:"Error in container_on_Click Method"}])}},jALDropdown:function(){try{if(f.validate()){f.extendData();f.instantiatePlugin()}}catch(k){$(h.currentObj).trigger("OnError",[{Message:"Error in jALDropdown Method"}])}},extendData:function(){try{h.json_Extend=$.extend({},h.defaultSettings,d)}catch(k){$(h.currentObj).trigger("OnError",[{Message:"Error in extendData Method"}])}},validate:function(){try{if(d.EventHandlers.OnError!==undefined&&d.EventHandlers.OnError!==null&&d.EventHandlers.OnError!==""){$(h.currentObj).bind("OnError",d.EventHandlers.OnError);if(!d.DataTextField){$(h.currentObj).trigger("OnError",[{Message:"Please enter valid DataTextField"}]);return false}else{if(d.DataTextField==""){$(h.currentObj).trigger("OnError",[{Message:"Please enter valid DataTextField"}])}else{if(!d.DataValueField){$(h.currentObj).trigger("OnError",[{Message:"Please enter valid DataValueField"}])}else{if(d.DataValueField==""){$(h.currentObj).trigger("OnError",[{Message:"Please enter valid DataValueField"}])}else{if(!d.Data){$(h.currentObj).trigger("OnError",[{Message:"Please enter valid Data"}])}else{if(d.Data.length>0&&(d.Data[0][d.DataTextField]==undefined||d.Data[0][d.DataTextField]==null)){$(h.currentObj).trigger("OnError",[{Message:"'"+d.DataTextField+"' key not found in data."}])}else{if(d.Data.length>0&&(d.Data[0][d.DataValueField]==undefined||d.Data[0][d.DataValueField]==null)){$(h.currentObj).trigger("OnError",[{Message:"'"+d.DataValueField+"' key not found in data."}])}else{return true}}}}}}}}else{$(h.currentObj).html("OnError Event not found!")}}catch(k){$(h.currentObj).trigger("OnError",[{Message:k.message}])}},instantiatePlugin:function(){try{$(h.currentObj).attr("type",h.type);$(h.currentObj).attr("defaultheight",$("#PnlListWrap"+e).height());f.createHtml();f.attachInternalEvents()}catch(k){$(h.currentObj).trigger("OnError",[{Message:"Error in instantiatePlugin Method"}])}},container_scroll:function(){var k=$(this).attr("id").substr(5);$("#PnlList"+k).attr("OnList","false")},display_MouseOver:function(){var k=$(this).attr("id").substr(5);$("#TxtDD"+k).attr("onOver","true")},display_MouseOut:function(){var k=$(this).attr("id").substr(5);$("#TxtDD"+k).attr("onOver","false")},display_click:function(){var k=$(this).attr("id").substr(5);$("#Btn"+k).click()},createHtml:function(){try{var l=[];var k=[];var n=$("#PnlListWrap"+h.containerId);$(h.containerId).attr("customAttribute","pluginconsumed");l.push('<div DataTextField="'+h.json_Extend.DataTextField+'" DataValueField="'+h.json_Extend.DataValueField+'" id="PnlCntnr'+h.containerId+'" ');if(j.Structure.Template&&j.Structure.Template!=""){l.push('class="template-control-wrapper"')}else{l.push('class="control-wrapper"')}l.push('style="" >');l.push('<span type="selectedtext" class="innertext"');l.push('id="TxtDD'+h.containerId+'" onOver="false">');l.push('</span><button id="Btn'+h.containerId+'"');if(j.Structure.Template&&j.Structure.Template!=""){l.push('type="button" class="ui-state-default ui-template-dropdown-button"><span class="ui-icon ui-icon-triangle-1-s "></span></button></div>')}else{l.push('type="button" class="ui-state-default ui-dropdown-button"><span class="ui-icon ui-icon-triangle-1-s "></span></button></div>')}k.push('<div class="'+h.json_Extend.cssStyle.itemContainerCss+'" type="ITEMDIVWRAP" id="PnlListWrap'+h.containerId+'"  style="position:relative; padding-right:1px;" ><div type="ITEMDIV"  id="PnlList'+h.containerId+'"');k.push('class="ui-widget-content"');k.push('style="border:none;');k.push('"><dl id="DlList'+h.containerId+'">');if(j.Structure.Template&&j.Structure.Template!=""){$.each(d.Data,function(p){k.push('<dt class="'+h.customitem+'" dtCurrent="hide" itemval="');k.push(d.Data[p][d.DataValueField]);k.push('" ');if(d.Data[p].attributes){for(var q=0;q<d.Data[p].attributes.length;q++){k.push(d.Data[p].attributes[q].name+'="'+d.Data[p].attributes[q].value+'" ')}}k.push(" >");var r=f.createTemplate(j.Structure.Template,j.Structure.TemplateParams,d.Data,p);k.push(r);k.push("</dt>")})}else{$.each(d.Data,function(p){k.push('<dt class="'+h.customitem+'" dtCurrent="hide" itemval="');k.push(d.Data[p][d.DataValueField]);k.push('" itemtext="');k.push(d.Data[p][d.DataTextField]);k.push('" ');if(d.Data[p].attributes){for(var q=0;q<d.Data[p].attributes.length;q++){k.push(d.Data[p].attributes[q].name+'="'+d.Data[p].attributes[q].value+'" ')}}k.push(" >");k.push(d.Data[p][d.DataTextField]);k.push("</dt>")})}k.push("</dl></div></div>");$("#"+h.containerId).empty().append(l.join(""));$("#PnlListWrap"+h.containerId).remove();$("body").append(k.join(""));$("#PnlCntnr"+h.containerId).data("Template",j.Structure.Template);$("#PnlCntnr"+h.containerId).data("TemplateParams",j.Structure.TemplateParams);f.hidePanel(h.containerId);if(j.Structure.Template&&j.Structure.Template!=""){strTemplate=j.Structure.Template;for(var o=0;o<j.Structure.TemplateParams.length;o++){strTemplate=strTemplate.replace(j.Structure.TemplateParams[o].Key,d.Data[0][j.Structure.TemplateParams[o].value])}if(d.Data.length>0){f.setItemValue(h.containerId,d.Data[0][d.DataValueField],strTemplate)}}else{if(d.Data.length>0){f.setItemValue(h.containerId,d.Data[0][d.DataValueField],d.Data[0][d.DataTextField])}}}catch(m){$(h.currentObj).trigger("OnError",[{Message:m.message}])}},attachInternalEvents:function(){try{var m=$("#PnlListWrap"+h.containerId);var l=$("#TxtDD"+h.containerId);$("#PnlListWrap"+h.containerId).unbind("click").bind("click.jALDropDownEvents",f.container_on_Click);$("#PnlListWrap"+h.containerId).unbind("mouseover").bind("mouseover.jALDropDownEvents",f.divMouseOver);$("#PnlListWrap"+h.containerId).unbind("mouseout").bind("mouseout.jALDropDownEvents",f.divMouseLeave);$(h.currentObj).unbind("OnChange").bind("OnChange",h.json_Extend.EventHandlers.OnChange);$("#PnlList"+h.containerId).unbind("scroll").bind("scroll.jALDropDownEvents",f.container_scroll);$("#"+h.containerId).unbind("keydown").bind("keydown.jALDropDownEvents",f.container_on_KeyDown);$("#Btn"+h.containerId).unbind("click").bind("click.jALDropDownEvents",f.showList);$("#Btn"+h.containerId).unbind("blur").bind("blur.jALDropDownEvents",f.hideList);$("#Btn"+h.containerId).unbind("focus").bind("focus.jALDropDownEvents",f.pnlFocus);$("#TxtDD"+h.containerId).unbind("mouseover").bind("mouseover.jALDropDownEvents",f.display_MouseOver);$("#TxtDD"+h.containerId).unbind("mouseout").bind("mouseout.jALDropDownEvents",f.display_MouseOut);$("#TxtDD"+h.containerId).unbind("click").bind("click.jALDropDownEvents",f.display_click)}catch(k){$(h.currentObj).trigger("OnError",[{Message:"Error in attachInternalEvents Method"}])}},createTemplate:function(m,p,l,k){try{for(var o=0;o<p.length;o++){m=m.replace(p[o].Key,l[k][p[o].value])}return(m)}catch(n){$(h.currentObj).trigger("OnError",[{Message:"Error in createTemplate Method"}])}},getValue:function(){try{return $("#"+h.containerId).attr("itemval")}catch(k){$(h.currentObj).trigger("OnError",[{Message:"Error in getValue Method"}])}},getSelectedItem:function(){try{return $("#PnlListWrap"+h.containerId).find("dt").filter("[itemval='"+f.getValueForAttribute($("#"+h.containerId).attr("itemval"))+"']")}catch(k){$(h.currentObj).trigger("OnError",[{Message:"Error in getSelectedItem Method"}])}},destroyEvents:function(){try{$("#PnlListWrap"+h.containerId).unbind(".jALDropDownEvents");$("#PnlList"+h.containerId).unbind(".jALDropDownEvents");$("#TxtDD"+h.containerId).unbind(".jALDropDownEvents");$("#Btn"+h.containerId).unbind(".jALDropDownEvents");$("#"+h.containerId).unbind(".jALDropDownEvents");$("#"+h.containerId).empty()}catch(k){$(h.currentObj).trigger("OnError",[{Message:"Error in getSelectedItem Method"}])}},setItemValue:function(l,k,m){try{$("#"+l).attr("itemval",k);$("#TxtDD"+l).html(m);if(j.Structure==""){$("#TxtDD"+l).attr("title",m)}$("#"+l).attr("itemval",k)}catch(n){$(h.currentObj).trigger("OnError",[{Message:"Error in setItemValue Method"}])}},setDropDownData:function(l,m){var t=$("#PnlCntnr"+h.containerId);var k=t.attr("DataTextField");var p=t.attr("DataValueField");var n=[];var r;var o;if(m.Template&&m.Template!=""){r=m.Template}else{r=t.data("Template")}if(m.TemplateParams&&m.TemplateParams!=""){o=m.TemplateParams}else{o=t.data("TemplateParams")}try{if(m.Template&&m.Template!=""){$.each(l,function(u){n.push('<dt  class="'+h.customitem+'" dtCurrent="hide" itemval="');n.push(l[u][p]);n.push('" ');if(l[u].attributes){for(var v=0;v<l[u].attributes.length;v++){n.push(l[u].attributes[v].name+'="'+l[u].attributes[v].value+'" ')}}n.push(" >");var w=f.createTemplate(r,o,l,u);n.push(w);n.push("</dt>")})}else{$.each(l,function(u){n.push('<dt class="'+h.customitem+'" dtCurrent="hide" itemval="');n.push(l[u][p]);n.push('" ');if(l[u].attributes){for(var v=0;v<l[u].attributes.length;v++){n.push(l[u].attributes[v].name+'="'+l[u].attributes[v].value+'" ')}}n.push(" >");n.push(l[u][k]);n.push("</dt>")})}$("#DlList"+h.containerId).remove();$("#PnlListWrap"+h.containerId).css("height","");$("#DlList"+h.containerId).css("height","");$("#PnlList"+h.containerId).append('<dl id="DlList'+h.containerId+'">'+n.join("")+"</dl>");if(r&&r!=""){t.addClass("template-control-wrapper")}else{t.addClass("control-wrapper")}if(r&&r!=""){for(var s=0;s<o.length;s++){r=r.replace(o[s].Key,l[0][o[s].value])}f.setItemValue(h.containerId,l[0][p],r)}else{f.setItemValue(h.containerId,l[0][p],l[0][k])}}catch(q){$(h.currentObj).trigger("OnError",[{Message:"Error in setDropDownData Method"}])}},setValue:function(k){try{$("#PnlListWrap"+h.containerId).find("dt").each(function(){if($(this).attr("itemval")==k){f.setItemValue(h.containerId,k,$(this).html())}});return this}catch(l){$(h.currentObj).trigger("OnError",[{Message:"Error in setValue Method"}])}},addItem:function(k){try{if(d.Action.ActionParams&&d.Action.ActionParams!=""){$('<dt dtCurrent="hide" itemtext="'+d.Action.ActionParams[0].text+'" itemval="'+d.Action.ActionParams[0].value+'">'+d.Action.ActionParams[0].text+"</dt>").insertBefore($("#DlList"+h.containerId+" dt:eq("+d.Action.ActionParams[0].index+")"));return this}}catch(l){$(h.currentObj).trigger("OnError",[{Message:"Error in addItem Method"}])}},removeItem:function(k){try{if(d.Action.ActionName&&d.Action.ActionParams[0].value&&d.Action.ActionParams!=""){$("#PnlListWrap"+h.containerId).find("dt").each(function(){if($(this).attr("itemval")===k){$(this).remove();if($("#"+e).attr("itemval")==k){f.setItemValue(h.containerId,$("#PnlListWrap"+h.containerId).find("dt:eq(0)").attr("itemval"),$("#PnlListWrap"+h.containerId).find("dt:eq(0)").attr("itemtext"))}}})}}catch(l){$(h.currentObj).trigger("OnError",[{Message:"Error in removeItem Method"}])}},setFirst:function(){try{f.setItemValue(h.containerId,$("#DlList"+h.containerId).find("dt:first").attr("itemval"),$("#DlList"+h.containerId).find("dt:first").html())}catch(k){$(h.currentObj).trigger("OnError",[{Message:"Error in setFirst Method"}])}},setLast:function(){try{f.setItemValue(h.containerId,$("#DlList"+h.containerId+" dt:last").attr("itemval"),$("#DlList"+h.containerId+" dt:last").html())}catch(k){$(h.currentObj).trigger("OnError",[{Message:"Error in setLast Method"}])}},isNumeric:function(m){var l="0123456789";for(var k=0;k<m.length;k++){if(l.indexOf(m.charAt(k))==-1){return false}}return true},setIndex:function(k){try{f.setItemValue(h.containerId,$("#DlList"+h.containerId+" dt").eq(k).attr("itemval"),$("#DlList"+h.containerId+" dt").eq(k).html())}catch(l){$(h.currentObj).trigger("OnError",[{Message:"Error in index Method"}])}},controlEnable:function(){try{var l=$("#Btn"+h.containerId);$("#"+h.containerId).css("color","");$("#"+h.containerId).removeAttr("disabled");l.removeAttr("disabled");l.addClass("ui-state-default");l.removeClass("ui-state-disabled");$("#TxtDD"+h.containerId).removeAttr("disabled")}catch(k){$(h.currentObj).trigger("OnError",[{Message:"Error in controlEnable Method"}])}},controlDisable:function(){try{var l=$("#Btn"+h.containerId);$("#"+h.containerId).css("color","#a2a2a2");$("#"+h.containerId).attr("disabled",true);l.attr("disabled",true).attr("OnList","false");l.removeClass("ui-state-default");l.addClass("ui-state-disabled");f.hidePanel(h.containerId);$("#TxtDD"+h.containerId).attr("disabled",true)}catch(k){$(h.currentObj).trigger("OnError",[{Message:"Error in controlDisable Method"}])}}};if(d.Action){var a;var g;if(d.Action.ActionName){switch(d.Action.ActionName.toLowerCase()){case"getselectedvalue":a=f.getValue();break;case"getselecteditem":a=f.getSelectedItem();break;case"getselectedtext":g=f.getSelectedItem().text();break;default:a=undefined;break}}if(a){return a}if(g){return g}}return this.each(function(){h.containerId=$(this).attr("id");h.currentObj=this;if(!d.Action){if(f.validate()){f.jALDropdown()}}else{if($(h.containerId).attr("customAttribute","pluginconsumed")){if(d.Action.ActionName&&d.Action.ActionName.toUpperCase()=="SETINDEX"){if(d.Action.ActionParams&&d.Action.ActionParams!=""){switch(d.Action.ActionParams.toUpperCase()){case"FIRST":f.setFirst();break;case"LAST":f.setLast();break;default:if($("#DlList"+h.containerId+" dt").length>d.Action.ActionParams){if(f.isNumeric(d.Action.ActionParams)){f.setIndex(d.Action.ActionParams)}}break}}}switch(d.Action.ActionName.toLowerCase()){case"enable":f.controlEnable();break;case"disable":f.controlDisable();break;case"destroy":f.destroyEvents();break;case"additem":f.addItem();break;case"removeitem":f.removeItem(d.Action.ActionParams[0].value);break;case"setvalue":f.setValue(d.Action.ActionParams);break;case"setdata":if(d.Action.ActionParams&&d.Action.ActionParams.Data){return f.setDropDownData(d.Action.ActionParams.Data,j.Structure)}break;default:break}}}})}catch(c){throw c}};