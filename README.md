# HomeCast-file-server-2.0
Jest to server, który uruchamiamy na komputerze, z którego będziemy chcieli strumieniować filmy.
Część projektu na androida znajduje się w tym repozytorium [HomeCast](https://github.com/Athulu/HomeCast).


### Uruchomienie
1. Skompilowaną wersję uruchamiamy na komputerze, z którego chcemy chcemy strumieniować filmy za pomocą komendy java -jar nazwa-pliku.jar.
2. Aplikacja w terminalu pokaże IP komputera wraz z portem, z którego wystartowała. Wystarczy przepisać adres IP oraz numer portu do ustawień aplikacji androidowej.
3. Filmy, które chcemy żeby były widoczne do strumieniowania w naszej aplikacji wrzucamy do folderu C:\HomeCast\mp4.
4. Pamiętać, że telewizor, telefon i komputer, na którym uruchamiamy server muszą być w tej samej sieci oraz o Chromecast lub Google TV.
5. Opcjonalnie przygotowany jest prosty formularz pod adresem, z którego wystartuje server. Możemy w formularzu ustawić token ChatGPT API. Pozwoli nam na automatyczne generowanie opisów do filmów, żeby aplikacja nie była pusta.

![Zrzut ekranu 2023-08-18 033306](https://github.com/Athulu/HomeCast-file-server-2.0/assets/56313840/44f99fd2-968b-4240-8dd8-dbdcba6a08c4)
![HomeCast ustawienia](https://github.com/Athulu/HomeCast-file-server-2.0/assets/56313840/e89afbe1-2cbd-420f-80e8-f234af0f7390)

### O aplikacji
Włączając filmy z komputera na telewizorze byłem za każdym razem zmuszony do jednej z tych irytujących opcji:
1. Strumieniować bezpośrednio na telewizor, ale za każdym razem, gdy było trzeba zatrzymać/zmienić film/przewinąć to byłem zmuszony iść do mojego pokoju.
2. Przenosić laptopa do salonu razem z zasilaniem, aby podłączyć pod HDMI. Dalej byłem zmuszony wstawać z kanapy gdy chciałem zatrzymać lub zmienić film. Mniej chodzenia, ale więcej noszenia.

Potrzebowałem aplikacji podobnej do tych, które oferują popularne platformy streamingowe, ale która będzie korzystać z lokalnej bazy filmów.
I tak rozpoczęła się praca nad domowym serverem strumieniującym i zarządzającym filmami.
