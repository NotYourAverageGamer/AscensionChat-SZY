package wowchat.game

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

/**
  * This class provides functionality for encrypting and decrypting game headers specific to
  * World of Warcraft: Wrath of the Lich King (WotLK) expansion.
  *
  * It extends the GameHeaderCrypt class and overrides the init, decrypt, and encrypt methods
  * to implement custom encryption and decryption logic.
  */
class GameHeaderCryptWotLK extends GameHeaderCrypt {

  private var clientCrypt: RC4 = _
  private var serverCrypt: RC4 = _

  /**
    * Server HMAC seed for WotLK expansion.
    */
  protected val serverHmacSeed: Array[Byte] = Array(
    0xCC, 0x98, 0xAE, 0x04, 0xE8, 0x97, 0xEA, 0xCA, 0x12, 0xDD, 0xC0, 0x93, 0x42, 0x91, 0x53, 0x57
  ).map(_.toByte)

  /**
    * Client HMAC seed for WotLK expansion.
    */
  protected val clientHmacSeed: Array[Byte] = Array(
    0xC2, 0xB3, 0x72, 0x3C, 0xC6, 0xAE, 0xD9, 0xB5, 0x34, 0x3C, 0x53, 0xEE, 0x2F, 0x43, 0x67, 0xCE
  ).map(_.toByte)

  /**
    * Decrypts the provided data using server encryption.
    *
    * @param data The data to decrypt.
    * @return The decrypted data.
    */
  override def decrypt(data: Array[Byte]): Array[Byte] = {
    if (!_initialized) {
      return data
    }

    serverCrypt.cryptToByteArray(data)
  }

  /**
    * Encrypts the provided data using client encryption.
    *
    * @param data The data to encrypt.
    * @return The encrypted data.
    */
  override def encrypt(data: Array[Byte]): Array[Byte] = {
    if (!_initialized) {
      return data
    }

    clientCrypt.cryptToByteArray(data)
  }

  /**
    * Initializes the crypt with the provided key and custom HMAC seeds.
    *
    * @param key The key used for encryption and decryption.
    */
  override def init(key: Array[Byte]): Unit = {
    val md = Mac.getInstance("HmacSHA1")

    md.init(new SecretKeySpec(serverHmacSeed, "HmacSHA1"))
    md.update(key)
    val serverKey = md.doFinal()

    md.init(new SecretKeySpec(clientHmacSeed, "HmacSHA1"))
    md.update(key)
    val clientKey = md.doFinal()

    serverCrypt = new RC4(serverKey)
    serverCrypt.cryptToByteArray(new Array[Byte](1024))
    clientCrypt = new RC4(clientKey)
    clientCrypt.cryptToByteArray(new Array[Byte](1024))

    _initialized = true
  }
}
