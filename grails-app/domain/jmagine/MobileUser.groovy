package jmagine

/**
 * jmagine.MobileUser is used to track mobile users, each time a user install the application, a unique ID is generated
 * and helps tracking bad behaviour with comment service
 * Could be used to save information about user devices to get some usage stats
 */
class MobileUser {

    String  mobileId // unique ID generated mobile side along the first installation / launch
    Date    dateCreated

    static constraints =
    {
        mobileId blank: false, unique: true
    }
}
