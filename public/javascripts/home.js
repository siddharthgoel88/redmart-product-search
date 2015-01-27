$(document).ready(function(){

	onReady();
	
});



function onReady(){

	attachPlugins();

	$("#update").on("click",onUpdateClick);

}


function onUpdateClick(){

	var id = $("#_prid").text(),
		title = $("#_title").val(),
		price = $("#_price").val();
		
		
	$.ajax({
		url: "/update",
        type:"POST",
        data:{id:id,title:title,price:price},
        success:function(data){
          console.log(data)
        	if(data.success == true){

        		$("#msg").removeClass("red").addClass("green").text(data.message);
        	}else{
        		$("#msg").removeClass("green").addClass("red").text(data.message);
        	}
        },
        error:function(err){
          console.log(err.responseText);
        	$("#msg").removeClass("green").addClass("red").text(err.responseText);
        }


	});

}

function attachPlugins(){	
	
	 $( "#ps" ).autocomplete({
      source: function( request, response ) {
        $.ajax({
          url: "/product-search",
          type:"POST",
          data: {
            productid: request.term
          },
          success: function( data ) {          	
          	var ids = [];
          	for(var i in data){
          		ids.push({"label":data[i].id,"val":data[i]});
          	}
            response( ids.slice(0,10) );
          }
        });
      },
      minLength: 1,
      select: function( event, ui ) {
      	console.log(ui.item);
      	$("#_prid").text(ui.item.val.id);
      	$("#_title").val(ui.item.val.title);
      	$("#_price").val(ui.item.val.pricing.price);
      	$("#_cost").text(ui.item.val.pricing.cost);
      },
      open: function() {
        $( this ).removeClass( "ui-corner-all" ).addClass( "ui-corner-top" );
      },
      close: function() {
        $( this ).removeClass( "ui-corner-top" ).addClass( "ui-corner-all" );
      }
    });
}
