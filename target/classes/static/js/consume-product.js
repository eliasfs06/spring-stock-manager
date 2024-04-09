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
