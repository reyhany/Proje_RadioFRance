
Feature: Müzik dinleme özelliği

  Background:
    Given Uygulamayı başlatıyorum
    And Musique başlığına tıklıyorum
    And Sunulan ilk eseri dinlemek için Écouter butonuna tıklarım

  Scenario: Kullanıcı bir müzik parçasını dinler
    And Müziğin çalmaya başladığını doğrularım

  Scenario: Müzik ayar çubuğundaki butonların doğru çalışıp çalışmadığını kontrol etme
    When Müzik ayar çubuğundaki ses açma-kapama butonuna tıklarım
    And Müzik ayar çubuğundaki ses seviyesini artırırım
    And Müzik ayar çubuğundaki ses seviyesini azaltırım
    Then Butonların doğru şekilde çalıştığını doğrularım

  Scenario: Ayar çubuğundaki hız ayarlarını değiştirme ve değişiklikleri doğrulama
    When Kullanıcı Vitesse de lecture butonuna tıklar
    Then Kullanıcı hız ayarlarını sırayla seçer ve doğrular

  Scenario: Ayar çubuğunda küçültme ve büyütme
    When Kullanıcı Reduire Player butonuna tıklar ve ekran küçülür
    And Kullanıcı Agrandir Player butonuna tıklar ve ekran büyür
    Then Müzik ayar çubuğunu kapatır ve işlemi doğrular

  Scenario: Müzik ayar çubuğunda 15 saniye geri ve 30 saniye ileri düğmelerinin doğru çalışıp çalışmadığını kontrol etme
    When Kullanıcı 15 saniye geri düğmesine tıklar
    Then Müzik süresinin 15 saniye geri alındığını doğrular
    When Kullanıcı 30 saniye ileri düğmesine tıklar
    Then Müzik süresinin 30 saniye ileri alındığını doğrular

  Scenario: Müzik durdurma, başlatma ve Ecouter Plus Tard ile işlem yapma
    When Pause butonuna basarım ve müziği durdururum
    Then Müziğin durdurulduğunu doğrularım
    When Tekrar aynı düğmeye basarım ve müzik yeniden başlar
    Then Müziğin yeniden başladığını doğrularım
    When Ecouter Plus Tard butonuna basarım
    Then Se connecter menüsü açılır

Feature:  Se connecter ve S'inscrire Menüsü Kontrolleri
  @muz
  Scenario: Seconnecter menu ile ilgili kontroller
    Given Pause butonuna basarım ve müziği durdururum
    When Ecouter Plus Tard butonuna basarım
    Then Se connecter menüsü açılır
    And Menü üzerindeki bilgilendirici yazıların varlığını doğrularım
    When Se connecter butonuna basarım
    Then Giriş ekranının açıldığını doğrularım
    And Giriş ekranını kapatırım
    When Ecouter Plus Tard butonuna basarım
    Then Se connecter menüsü açılır
    When S'inscrire butonuna basarım
    Then Kayıt ekranının açıldığını doğrularım

  Scenario:




