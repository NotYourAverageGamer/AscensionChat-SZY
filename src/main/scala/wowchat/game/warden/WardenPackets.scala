package wowchat.game.warden

/**
  * Object containing constants for Warden protocol packets.
  */
object WardenPackets {
  // Client->Server
  val WARDEN_CMSG_MODULE_MISSING = 0x00
  val WARDEN_CMSG_MODULE_OK = 0x01
  val WARDEN_CMSG_CHEAT_CHECKS_RESULT = 0x02
  val WARDEN_CMSG_MEM_CHECKS_RESULT = 0x03
  val WARDEN_CMSG_HASH_RESULT = 0x04
  val WARDEN_CMSG_MODULE_FAILED = 0x05

  // Server->Client
  val WARDEN_SMSG_MODULE_USE = 0x00
  val WARDEN_SMSG_MODULE_CACHE = 0x01
  val WARDEN_SMSG_CHEAT_CHECKS_REQUEST = 0x02
  val WARDEN_SMSG_MODULE_INITIALIZE = 0x03
  val WARDEN_SMSG_MEM_CHECKS_REQUEST = 0x04
  val WARDEN_SMSG_HASH_REQUEST = 0x05
}
