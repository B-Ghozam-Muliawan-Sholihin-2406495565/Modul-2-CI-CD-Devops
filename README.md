<details>
<summary>Modul 1</summary>

## Refleksi 1
- Di modul ini, saya sudah menerapkan prinsip-prinsip dari clean code, seperti meaningful names, method, dan self-documenting code sehingga kita tidak perlu untuk comment lebih lanjut. Lalu saya juga berusaha untuk tidak mereturn null namun melakukan throw exception. Saya menemukan beberapa kesalahan di source code tutorial, seperti quantity product yang menerima int di model, namun form menerima text, yang saya ubah menjadi number. Saya juga menemukan kurangnya data validation, saya perbaiki dengan menambahkan required pada input nama, min=0 pada input quantity agar tidak bisa negatif pada html, saya juga menambahkan validasi di controller jika data yang tidak valid tersebut terselip lolos ke backend.

## Refleksi 2
- Menurut saya, unit test perlu ditambah sebanyak sampai coveragenya 85% atau 90%, sehingga kita bisa mengetahui bahwa mayoritas kode yang kita buat sudah dicek dan benar-benar jalan untuk testcase yang kita jalankan. Tapi 100% test coverage hanya memverifikasi kalau kode sudah sesuai dengan test case kita, jadi belum tentu source code sudah bugfree / error free, karena bisa aja ada kode input validation yang belum tertulis, yang otomatis belum dites, sehingga memunculkah celah pada source code.

- Kalau dibuat test baru dengan setup dan instance variable yang sama, maka kualitas dan cleanliness kode akan menurun, karena kode jadi akan terduplikat, dan melanggar konsep clean code "Dont Repeat Yourself" sehingga kode yang terduplikat menjadi redundant, serta jika ada kode yang duplikat di 2 file atau lebih, lalu ada update dalam source code, maka kita harus mengupdate setiap testnya. Solusinya kita bisa menggunakan konsep inheritance atau abstract class, dimana setup dan instantiate variables bisa dilakukan di class tersebut, lalu test-test selanjutnya tinggal meng-inherit abstract class tadi. 
</details>

<details>
<summary>Modul 2</summary>

- Sebelum kode pertama modul 2 saya push ke github, saya menyadari unit-test masih belum mengikuti prinsip DRY, dimana instansiasi class masih per-test, bukan di Set-Up. Saya perbaiki mengikuti DRY dengan set 1 class awal di Set-Up, dan edit variable pada test jika perlu. Setelah saya push ke github dan scan, ternyata ada 1 security hotspot, yang dimana memerlukan verifikasi dependensi dalam file `verification-metadata.xml`. Untuk menambah file tersebut, saya jalankan command `./gradlew --write-verification-metadata help` pada terminal.

- Menurut saya, aplikasi di modul 2 ini sudah memenuhi definisi CI/CD. CI dilakukan pada semua branch dan main untuk PR, yang dimana dilakukan pengecekan kualitas kode oleh github actions untuk melakukan unit-test dan sonarcloud untuk pengecekan kualitas kode secara umum. CD akan dilakukan kalau semua step CI sudah berhasil pada PR dan sudah sukses merge ke main. Setelah itu program akan langsung di redeploy ke koyeb.
</details>

<details>
<summary>Modul 3</summary>

1. - Saya menerapkan SRP pada Controller dan Service, dimana sebelumnya Product Controller mengandung endpoint untuk Car juga, maka endpoint tersebut saya pindahkan ke CarController.java. Untuk Service, Product dan Car

- 
</details>