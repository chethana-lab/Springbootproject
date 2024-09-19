Service layer
-------------

public void deleteProduct(int id)
{
inventoryRepository.deleteById(id);
}



public Product updateProduct(int id, Product newProduct)
{
Optional<Product> existing = inventoryRepository.findById(id);
if(existing.isPresent()) {
Product p = existing.get();
p.setPname(newProduct.getName());
p.setPrice(newProduct.getPrice());
p.setQty(newProduct.getPqty());
return inventoryRepository.save(p);
}
else{
throw new RuntimeException("product not available");
}
}

public Optional<Product> getProduct(int id)
{
return inventoryRepository.findById(id);
}

Controller layer
----------------

@DeleteMapping("/products/{id}")
public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        inventoryService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
}

@PutMapping("/products/{id}")
public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Product updatedProduct = inventoryService.updateProduct(id, product);
        return ResponseEntity.ok(updatedProduct);
}


@GetMapping("/products/{id}")
public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = inventoryService.getProductById(id);
if(product.isPresent()){
return ResponseEntity.Ok(product.get());
}
else{
throw new RuntimeException("product not avaialble");
}
        //return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
}