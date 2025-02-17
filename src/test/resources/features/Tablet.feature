Feature: Tablet Satın Alma Senaryosu

  Scenario: : Kullanıcı Apple tablet filtreleyip, en yüksek fiyatlı ürünü sepete ekler ve fiyatları doğrular

  Given kullanıcı "https://www.hepsiburada.com/" sayfasına gider
  When kullanıcı "Tüm Kategoriler -> Elektronik -> Tablet" kategorisine geçer
  And kullanıcı Marka filtresinden "Apple" seçimini yapar
  And kullanıcı Ekran Boyutu filtresinden "13,2 inç" seçimini yapar
  And kullanıcı sıralama filtresi kullanılmadan çıkan sonuçlardan en yüksek fiyatlı ürünü seçer
  And kullanıcı ürün detay sayfasındaki "Sepete Ekle" butonuna tıklar
  Then sepetteki ürün, ürün detay sayfasındaki fiyat ile aynı olmalıdır
