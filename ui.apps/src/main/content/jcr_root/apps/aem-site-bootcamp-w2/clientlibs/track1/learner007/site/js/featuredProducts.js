let products=[];
async function loadFeaturedProducts(){
    const container = document.querySelector('.featured-Products-grid');
    if(!container){
        return
    }
    try{
        const response = await fetch('https://dummyjson.com/products');
        if(!response.ok){
            throw new Error('DB connection error')
        }
       let data = await response.json();       
        products.push(...data.products)
         renderProducts(products)
    }catch(err){
        console.log(err.message)
        container.innerHTML=`
        <div class="col-12">Unable to load products</div>
        `
    }
}

function renderProducts(products){
    const container = document.querySelector('.featured-Products-grid');
    if(!container){
        return
    }    
    let cartHtml=products.map(product=>`
            <div class="col-4">
            <div class="featured-Products-card" id="${product.id}">
                <div class="featured-Products-card_image">
                    <div class="featured-Products-card_image_img">
                        <img src="${product.images}" alt="${product.title}"/>
                    </div>
                </div>
                <div class="featured-Products-card_body">
                    <h3>${product.title}</h3>
                    <span aria-label="Price ${product.price} dollars">$${product.price}</span>
                    <button type="button" data-product-id="${product.id}" aria-label="Add ${product.title} to cart">Add to cart</button>
                </div>
            </div>
        </div>
            `).join('');
     container.innerHTML = cartHtml;
}

document.addEventListener('click',(e)=>{
    const prodcutID=e.target.dataset.productId;
    if(!prodcutID) return;
    addToCart(Number(prodcutID))
});

function addToCart(productID){
    let cart = getCart();
    const product = products.find((item)=>item.id===productID)
    if(!product) return;
    const exitingProduct = cart.find((item)=>item.id === productID)
    if(exitingProduct){
        exitingProduct.quantity +=1;
    }else{
        cart.push({
            ...product,
            quantity:1
        })
    }
    saveCart(cart);
    updateCartCount();    
    renderCart();
}


function changeProductHeading() {
    const currentPage = window.location.pathname.split('/').pop();
    if (currentPage !== 'home.html' && currentPage !== 'shop.html') {
        return;
    }
    const heading = document.querySelector('.product-container h2');
    if (!heading) return;
    heading.innerText =
        currentPage === 'home.html'
            ? 'Featured Products'
            : 'All Products';
}

document.addEventListener('DOMContentLoaded', changeProductHeading);
document.addEventListener('DOMContentLoaded',()=>{
    changeProductHeading();
    loadFeaturedProducts();
});