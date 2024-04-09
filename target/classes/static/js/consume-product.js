$(document).ready(function(){
    $('a[data-target="#productConsumeModal"]').click(function(){
        var productId = $(this).data('product-id');
        $('#productId').val(productId);
    });
});

const consumeProduct = () => {
    let productId =  $('#productId').val();
    let quantity = $('#consume-quantity').val();
    $.ajax({
        url: `/product/consume/${productId}?quantity=${quantity}`,
        type: 'POST',
        success: function () {
            $('#productConsumeModal').modal('hide');
            Swal.fire({
                icon: 'success',
                title: 'Success',
                text: 'Product consumed successfully.',
                onClose: () => {
                    window.location.href = '/product/list';
                }
            });
        },
        error: function (xhr, status, error) {
            let errorMessage = xhr.responseText;
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: errorMessage,
                confirmButtonText: 'OK',
            });
        }
    });
}


$(document).ready(function(){
    $('a[data-target="#productConsumeModal"]').click(function(){
        var productId = $(this).data('product-id');
        $('#productId').val(productId);
    });

    $('a[data-target="#productConsumeRequestModal"]').click(function(){
        var productId = $(this).data('product-id');
        $('#productId-request').val(productId);
    });
});

const requestToConsumeProduct = () => {
    let productId =  $('#productId-request').val();
    let quantity = $('#consume-request-quantity').val();
    let description = $('#consume-description').val();

    $.ajax({
        url: `/product/request-to-consume/${productId}?quantity=${quantity}`,
        type: 'POST',
        data: { description: description },
        success: function () {
            $('#productConsumeRequestModal').modal('hide');
            Swal.fire({
                icon: 'success',
                title: 'Success',
                text: 'Request done successfully.',
                onClose: () => {
                    window.location.href = '/product/list';
                }
            });
        },
        error: function (xhr, status, error) {
            let errorMessage = xhr.responseText;
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: errorMessage,
                confirmButtonText: 'OK',
            });
        }
    });
}
