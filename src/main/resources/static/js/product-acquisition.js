function getItens() {
    let itens = [];
    $('tr.existing-item').each(function () {
        const productId = $(this).find('.product-id').text();
        const quantity = $(this).find('.quantity').text();
        itens.push({productId: productId, quantity: quantity});
    });

    return itens;
}

const addProduct = () => {
    let itens = getItens();

    const newProductId = $('#item-product').val();
    const newQuantity = $('#item-quantity').val();
    if (newProductId && newQuantity) {
        itens.push({productId: newProductId, quantity: newQuantity});
    }

    $.ajax({
        url: '/product-acquisition/add-item',
        type: 'POST',
        data: JSON.stringify(itens),
        contentType: 'application/json',
        success: function (data) {
            $('#acquisition-itens').replaceWith(data);
        },
        error: function (xhr, status, error) {
            console.error("Error adding product:", status, error);
        }
    });
}

const saveProductAcquisition = () => {
    let itens = getItens();

    $.ajax({
        url: '/product-acquisition/save',
        type: 'POST',
        data: JSON.stringify(itens),
        contentType: 'application/json',
        success: function () {
            window.location.href = '/product-acquisition/list';
        },
        error: function (xhr, status, error) {
            console.error("Error saving product acquisition:", status, error);
        }
    });
}
