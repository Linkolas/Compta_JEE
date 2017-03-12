$(function() {
    $("#add_parts").click(function() {
        addTableRow();
        
        if($("input#isAutomatique").is(':checked')) {
            autoCalcul();
        }
    });
    
    $('form#form_creerfacture').bind('submit', function () {
        $(this).find(':input').prop('disabled', false);
    });
    
    $("input#isAutomatique").change(function() {
        if (this.checked) {
            autoCalcul();
        } else {
            var participants_percent = $("input[name=participant_pourcent]");
            var participants_value = $("input.valeur_participant");
            participants_percent.prop('disabled', false);
            participants_value.prop('disabled', false);
        }
    });
    
    $("input[name=total]").keyup(function() {
        $("input[name=participant_pourcent]").keyup();
    });
});

function addTableRow(){
    var new_content = "";
    new_content += "<tr><td>";

    new_content += "<select name=\"participant_id\">";
    new_content += $("select#payeur").html();
    new_content += "</select>";

    new_content += "</td><td><input class=\"pourcentage\" type=\"number\" step=\"0.01\" min=\"0\" max=\"100\" value=\"0\" name=\"participant_pourcent\" onkeyup=\"onPercentChange(this)\"/>%</td>";
    new_content += "</td><td><input class=\"valeur_participant\" type=\"number\" step=\"0.01\" min=\"0\" value=\"0\" onkeyup=\"onValueChange(this)\"/>%</td>";
    
    new_content += "<td><button type=\"button\" onclick=\"deleteTableRow(this)\">Supprimer</button></td>";
    
    new_content += "</td></tr>";
    $("table#participants").append(new_content);
}

function deleteTableRow(elem) {
    $(elem).parent().parent().remove();
    
    if($("input#isAutomatique").is(':checked')) {
        autoCalcul();
    }
}

function autoCalcul() {
    var payeur_percent = $("input#pourcent_payeur");
    var payeur_value = $("input#valeur_payeur");
    var participants_percent = $("input[name=participant_pourcent]");
    var participants_value = $("input.valeur_participant");

    var count = participants_percent.length + 1;
    var valeur_percent = Math.round((100 / count) * 100) / 100;
    var valeur_value   = Math.round(valeur_percent * $("input[name=total]").val()) / 100;

    payeur_percent.val(valeur_percent);
    payeur_value.val(valeur_value);
    participants_percent.val(valeur_percent);
    participants_value.val(valeur_value);
    
    participants_percent.prop('disabled', true);
    participants_value.prop('disabled', true);
}

function onPercentChange(elem) {
    var input_valeur = $(elem).parent().parent().find("input.valeur_participant");
    var valeur = Math.round($(elem).val() * $("input[name=total]").val()) / 100;
    
    input_valeur.val(valeur);
    
    correctPayeur();
}

function onValueChange(elem) {
    var input_percent = $(elem).parent().parent().find("input[name=participant_pourcent]");
    var percent = Math.round(($(elem).val() / $("input[name=total]").val()) * 100 * 100) / 100;
    
    input_percent.val(percent);
    
    correctPayeur();
}

function correctPayeur() {
    var participants_percent = $("input[name=participant_pourcent]");
    var somme = 0;
    
    participants_percent.each(function() {
        somme += parseInt($(this).val());
    });
    
    var percent = 100 - somme;
    var value = Math.round(percent * $("input[name=total]").val()) / 100;
    
    $("input#pourcent_payeur").val(percent);
    $("input#valeur_payeur").val(value);
}