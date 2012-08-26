/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function sortAllSelects(){
    $$('select').each(function(e){
        sortCombo(e);
    });    
}

function sortCombo(combo){
    $A(combo.options).sort(function(a,b){
        return (a.text.toLowerCase() < b.text.toLowerCase() ) ? -1 : 1;
    }).each(function(e, i){
        combo.options[i] = new Option(e.text, e.value, false, e.selected);
    });        
}
