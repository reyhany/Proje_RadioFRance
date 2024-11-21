Feature: Müzik dinleme özelliği

  Scenario: Kullanıcı bir müzik parçasını dinlemeye başlar
    Given Uygulamayı başlatıyorum
    Then Musique başlığına tıklıyorum
    Then Sunulan ilk eseri dinlemek için Écouter butonuna tıklarım
    And Müziğin çalmaya başladığını doğrularım

  Scenario: Müzik ayar çubuğundaki butonların doğru çalışıp çalışmadığını kontrol etme
    Given Uygulamayı başlatıyorum
    And Musique başlığına tıklıyorum
    And Sunulan ilk eseri dinlemek için Écouter butonuna tıklarım
    When Müzik ayar çubuğundaki ses açma-kapama butonuna tıklarım
    And Müzik ayar çubuğundaki ses seviyesini artırırım
    And Müzik ayar çubuğundaki ses seviyesini azaltırım
    Then Butonların doğru şekilde çalıştığını doğrularım
