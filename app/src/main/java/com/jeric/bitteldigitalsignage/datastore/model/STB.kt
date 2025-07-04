package com.jeric.bitteldigitalsignage.datastore.model

object STB {
    var HOST = ""
        get() = field.ifEmpty { "http://127.0.0.1" }
    var PORT = ""
    var ROOM = ""
    var MAC_ADDRESS = ""
    var AREA_ID = ""
    var API_KEY = ""
    var FIRST_RUN = ""
        get() = field.ifEmpty { "0" }
    var END_DATE = ""
    var REMAINING_DAYS = ""
    var ALLOW_API_RUN_HOME = false
    var GUEST_ASSIGN_ID = ""
    var API_TOKEN = ""
    var TOKEN_EXPIRED_AT = ""
    var ROOM_ID = "null"
    var ROOM_ASSIGNMENT = "null"
    var ALLOW_API_RUN_TV = false
    var ROOM_UID = ""
    var IDLE = ""
        get() = field.ifEmpty { "1" }

}
