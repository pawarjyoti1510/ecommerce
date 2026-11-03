import json, re
P = {
1: [
("Galaxy M35 5G Smartphone (8GB/128GB)","Samsung","6.6-inch sAMOLED, 6000mAh battery, 50MP triple camera","Exynos 1380 processor, 120Hz Super AMOLED display, 25W fast charging and 4 years of OS updates.",16999,24499,120),
("Redmi Note 13 Pro (8GB/256GB)","Xiaomi","200MP camera, 1.5K AMOLED, 67W turbo charging","Snapdragon 7s Gen 2, 5100mAh battery, IP54 rating and Gorilla Glass Victus.",23999,28999,85),
("Inspiron 15 Laptop (i5, 16GB, 512GB SSD)","Dell","15.6-inch FHD, 13th Gen Intel Core i5, Windows 11","Thin and light laptop with backlit keyboard, MS Office 2021 and 1-year onsite warranty.",54990,72000,40),
("WH-1000XM5 Wireless Headphones","Sony","Industry-leading noise cancellation, 30 hr battery","Eight microphones for crystal-clear calls, multipoint connection and quick charge (3 min = 3 hr).",26990,34990,60),
("Airdopes 141 TWS Earbuds","boAt","42 hr playback, ENx tech, low-latency beast mode","IPX4 water resistance, ASAP charge and Bluetooth 5.3 with instant pairing.",1099,4490,500),
("43-inch 4K Ultra HD Smart LED TV","Xiaomi","Google TV, Dolby Vision, 30W speakers","Bezel-less design, Chromecast built-in, 2GB RAM, 16GB storage and Vivid Picture Engine.",26999,42999,35),
("Watch 2 Pro Smartwatch","Noise","1.85-inch AMOLED, Bluetooth calling, 100+ sports modes","Heart rate, SpO2 and sleep tracking with 7-day battery life and IP68 rating.",3499,7999,200),
("20000mAh Power Bank 22.5W","Ambrane","Fast charging, triple output, Type-C input","Lithium polymer cells with 12-layer protection; charges a phone up to 4 times.",1299,2999,300),
("Wi-Fi 6 Dual Band Router AX1500","TP-Link","1.5 Gbps speed, 4 antennas, OneMesh support","WPA3 security, beamforming and easy setup with the Tether app.",3299,5499,90),
("EOS R50 Mirrorless Camera with 18-45mm Lens","Canon","24.2MP APS-C sensor, 4K video, Dual Pixel AF","Compact body for creators with vari-angle touchscreen and Wi-Fi/Bluetooth.",69990,82995,15),
],
2: [
("Non-Stick Cookware Set (3 pcs)","Prestige","Kadai, fry pan and tawa with induction base","PFOA-free granite coating, toughened glass lid and cool-touch handles.",1899,3495,150),
("Mixer Grinder 750W (3 Jars)","Bajaj","Stainless steel jars, 3 speed control, overload protection","Rust-proof blades for wet and dry grinding; 2-year warranty on product.",2999,4999,110),
("Air Fryer 4.1L","Philips","Rapid Air technology, up to 90% less fat","Digital touch panel with 7 presets, dishwasher-safe basket and keep-warm mode.",7499,10995,70),
("Stainless Steel Water Bottle 1L (Pack of 2)","Milton","Leak-proof, BPA-free, keeps water cold","Food-grade 304 steel with screw cap, ideal for office, gym and travel.",699,1250,400),
("Ceramic Dinner Set (18 pcs)","Cello","Microwave-safe, chip-resistant floral design","Includes 6 dinner plates, 6 quarter plates and 6 bowls; dishwasher safe.",2499,4290,60),
("Electric Kettle 1.5L","Pigeon","1500W fast boil, auto shut-off, stainless steel","Boil-dry protection, 360-degree swivel base and cool-touch handle.",599,1195,350),
("Cotton Double Bedsheet with 2 Pillow Covers","Bombay Dyeing","144 TC pure cotton, king size","Soft, breathable fabric with colourfast print; machine washable.",899,1999,250),
("Wall Clock - Modern Silent Sweep","Ajanta","12-inch, non-ticking quartz movement","Matte finish frame with glass cover, ideal for living room and office.",549,999,180),
("Storage Containers Set (18 pcs)","Tupperware","Airtight, stackable kitchen storage","Food-grade, BPA-free plastic in assorted sizes for grains, spices and snacks.",1499,2499,140),
("Pressure Cooker 5L Aluminium","Hawkins","Inner lid design, ISI certified","Classic whistle pressure cooker for fast, fuel-efficient cooking; 5-year warranty.",1899,2425,130),
],
3: [
("Atomic Habits","Penguin Random House","An easy and proven way to build good habits","James Clear explains how tiny changes compound into remarkable results. Paperback, 320 pages.",499,799,300),
("The Psychology of Money","Jaico Publishing House","Timeless lessons on wealth, greed and happiness","Morgan Housel's 19 short stories on how people think about money. Paperback, 252 pages.",299,399,350),
("Clean Code","Pearson","A handbook of agile software craftsmanship","Robert C. Martin on writing readable, maintainable code with real Java examples. Paperback.",699,999,90),
("Effective Java (3rd Edition)","Pearson","Best practices for the Java platform","Joshua Bloch's 90 items covering Java 7, 8 and 9 features, lambdas and streams.",899,1199,75),
("Head First Design Patterns","O'Reilly","A brain-friendly guide to design patterns","Learn Strategy, Observer, Decorator, Factory and more through visual, practical lessons.",1099,1499,60),
("The Alchemist","HarperCollins","A fable about following your dream","Paulo Coelho's international bestseller about the shepherd boy Santiago. Paperback, 208 pages.",249,399,400),
("Ikigai","Penguin Random House","The Japanese secret to a long and happy life","Héctor García and Francesc Miralles on finding purpose and joy every day. Hardcover.",379,550,280),
("Rich Dad Poor Dad","Plata Publishing","What the rich teach their kids about money","Robert Kiyosaki's classic on financial literacy and building assets. Paperback.",329,499,320),
("Designing Data-Intensive Applications","O'Reilly","The big ideas behind reliable, scalable systems","Martin Kleppmann on databases, replication, partitioning, streams and distributed systems.",1499,1899,50),
("Wings of Fire","Universities Press","An autobiography of A.P.J. Abdul Kalam","The inspiring journey of India's Missile Man and former President. Paperback.",199,350,260),
],
4: [
("Vitamin C Face Serum 30ml","Minimalist","10% Vitamin C for glowing skin","Ethyl ascorbic acid with acetyl glucosamine; reduces dullness and dark spots. Fragrance-free.",569,699,220),
("Matte Lipstick - Ruby Red","Lakme","Long-lasting matte finish, enriched with Vitamin E","Highly pigmented, transfer-resistant colour that stays up to 16 hours.",399,550,300),
("Aqua Sunscreen Gel SPF 50 PA+++ 50g","Neutrogena","Lightweight, non-greasy broad spectrum protection","Water-resistant formula suitable for oily and combination skin.",449,599,250),
("Onion Hair Oil 250ml","Mamaearth","Reduces hair fall, promotes growth","Infused with onion and redensyl; free from mineral oil, silicones and parabens.",349,499,280),
("Hydrating Face Wash 100ml","Cetaphil","Gentle, soap-free cleanser for sensitive skin","Dermatologist recommended; cleans without stripping natural moisture.",379,449,260),
("Kajal Waterproof - Black","Maybelline","Smudge-proof, 24 hr long wear","Intense black colour, ophthalmologically tested and safe for eyes.",199,299,450),
("Hair Dryer 1200W","Philips","2 heat settings, cool shot, foldable handle","ThermoProtect temperature control for gentle drying; travel-friendly design.",1099,1795,90),
("Moisturising Body Lotion 400ml","Nivea","48 hr moisture with almond oil","Deep nourishing lotion for dry skin, quickly absorbed with no greasy feel.",329,450,240),
("Perfume Eau de Parfum 100ml - Ocean","Bella Vita","Long-lasting fresh aquatic fragrance","Notes of bergamot, sea salt and musk; lasts 8-10 hours.",599,1199,160),
("Niacinamide 10% Serum 30ml","The Derma Co","Controls oil and reduces blemishes","With zinc for clearer, smoother skin; non-comedogenic and suitable for all skin types.",499,599,210),
],
5: [
("Classic Building Bricks Set (500 pcs)","LEGO","Creative brick box for ages 4+","Assorted colourful bricks with wheels, windows and eyes for endless building ideas.",2999,3999,70),
("Remote Control Racing Car 1:16","Mirana","Rechargeable, 2.4GHz, 20 km/h top speed","Shock-resistant body with LED headlights; includes USB charging cable.",1299,2499,120),
("Wooden Alphabet Puzzle Board","Skillmatics","Learn A-Z with colourful wooden pieces","Non-toxic paint, chunky pieces for small hands; for ages 2-5 years.",449,799,200),
("Barbie Dreamhouse Adventures Doll","Mattel","Fashion doll with accessories","Doll with outfit, shoes and handbag; encourages imaginative storytelling play.",899,1299,140),
("Hot Wheels 5-Car Gift Pack","Mattel","Five 1:64 scale die-cast cars","Assorted authentic designs; collectible and great for track play.",599,799,260),
("Monopoly Classic Board Game","Hasbro","Buy, sell and trade properties","Family board game for 2-6 players, ages 8 and up.",999,1499,110),
("Soft Teddy Bear 3 ft","Archies","Huggable plush toy with bow","Super-soft fabric, child-safe stitching; perfect gift for birthdays.",1199,1999,80),
("Rubik's Cube 3x3 Speed Cube","Funskool","Smooth turning stickerless cube","Lightweight puzzle cube with corner cutting for speedcubing practice.",249,499,350),
("Kids Ride-On Bike with Lights and Music","Toyzone","Battery operated for ages 2-5","Rechargeable battery, forward/reverse, horn and USB music player.",4999,7999,25),
("Doctor Set Pretend Play Kit (15 pcs)","Webby","Role-play medical kit with carry case","Stethoscope, thermometer, syringe and more; BPA-free plastic for ages 3+.",399,799,190),
],
6: [
("Banarasi Silk Saree with Blouse Piece","Soch","Zari woven traditional saree","Rich Banarasi art silk with intricate gold zari border; 5.5 m saree plus 0.8 m blouse.",2499,4999,60),
("Cotton Anarkali Kurta with Dupatta","Biba","Printed flared kurta set","Pure cotton anarkali with mul-mul dupatta; comfortable for festive and daily wear.",1799,2999,100),
("Women's Straight Fit Jeans","Levi's","Mid-rise, stretchable denim","Classic five-pocket styling with soft stretch for all-day comfort.",1999,3299,130),
("Floral Maxi Dress","Libas","Flowy rayon dress with tie belt","V-neck, three-quarter sleeves and ankle length; ideal for brunches and vacations.",1299,2599,90),
("Kurta Palazzo Set","W","Straight kurta with matching palazzo","Viscose blend with block print, mandarin collar and side slits.",1599,2799,110),
("Chiffon Printed Saree","Satrani","Lightweight party wear saree","Soft chiffon with digital print and lace border; includes unstitched blouse piece.",899,2199,150),
("Women's Formal Shirt - White","Van Heusen","Slim fit, wrinkle-free cotton","Classic collar, full sleeves; perfect for office and formal occasions.",1199,1999,120),
("Lehenga Choli Set","Fabcartz","Embroidered semi-stitched lehenga","Georgette lehenga with sequin work, choli fabric and net dupatta for weddings.",3499,6999,40),
("Women's Winter Puffer Jacket","Roadster","Quilted, hooded, water-resistant","Lightweight insulation with zip pockets; keeps warm in cold weather.",1999,3999,70),
("Cotton Leggings (Pack of 3)","Jockey","Ankle length, high stretch","Super combed cotton with elastane for a perfect fit; black, navy and maroon.",899,1197,200),
],
}
slug=lambda s: re.sub(r'[^a-z0-9]+','-',s.lower()).strip('-')[:40]
out=[]
for cid, items in P.items():
    for n,b,s,d,pr,m,st in items:
        assert pr<=m
        out.append({"name":n,"brand":b,"summary":s,"description":d,"categoryId":cid,
            "price":float(pr),"mrp":float(m),"currency":"INR","stockQuantity":st,
            "imageUrl":f"https://picsum.photos/seed/{slug(n)}/600/600"})
json.dump(out,open("products.json","w",encoding="utf-8"),indent=2,ensure_ascii=False)
print(len(out))
