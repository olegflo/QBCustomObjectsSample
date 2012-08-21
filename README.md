QBCustomObjectsSample
=====================

This sample demonstrates basic REST QuickBlox API requests for CustomObjects module.

**Before run this sample, go to [QB admin panel](https://admin.quickblox.com/) with following credentials**

* login: **testcoobj**
* passwod: **testcoobj**


After running this code you should get log similar to following

```
08-21 19:59:27.641: DEBUG/QuickBlox Rest Api Test(29129): Auth
08-21 19:59:27.641: DEBUG/QuickBlox Rest Api Test(29129): {"session":{"application_id":792,"created_at":"2012-08-21T18:59:30Z","device_id":null,"id":207080,"nonce":8962,"token":"16a82295a964c894d63480c18713550c2979dd43","ts":1345575566,"updated_at":"2012-08-21T18:59:30Z","user_id":null}}
08-21 19:59:28.091: DEBUG/QuickBlox Rest Api Test(29129): Sign in
08-21 19:59:28.091: DEBUG/QuickBlox Rest Api Test(29129): {"user":{"id":10840,"owner_id":784,"full_name":null,"email":null,"login":"test","phone":null,"website":null,"created_at":"2012-08-21T16:41:57Z","updated_at":"2012-08-21T18:49:00Z","last_request_at":"2012-08-21T18:49:00Z","external_user_id":null,"facebook_id":null,"twitter_id":null,"blob_id":null,"user_tags":null}}
08-21 19:59:28.361: DEBUG/QuickBlox Rest Api Test(29129): Create custom object
08-21 19:59:28.361: DEBUG/QuickBlox Rest Api Test(29129): {"_id":"5033da9236c9ae7f6a000360","health":72,"power":4,"type":"type9","user_id":10840,"created_at":1345575570,"updated_at":1345575570}
08-21 19:59:28.721: DEBUG/QuickBlox Rest Api Test(29129): Update custom object
08-21 19:59:28.721: DEBUG/QuickBlox Rest Api Test(29129): {"_id":"5033da9236c9ae7f6a000360","created_at":1345575570,"health":72,"power":4,"type":"some_new_monster_type","updated_at":1345575571,"user_id":10840}
08-21 19:59:29.001: DEBUG/QuickBlox Rest Api Test(29129): Retrieve custom object instances
08-21 19:59:29.001: DEBUG/QuickBlox Rest Api Test(29129): [{"_id":"5033d38936c9ae8e6a0002e1","health":95,"power":515,"type":"type0","user_id":10840,"created_at":1345573769,"updated_at":1345573769},{"_id":"5033d4bd36c9ae8a6a0002f5","health":80,"power":673,"type":"type4","user_id":10840,"created_at":1345574077,"updated_at":1345574077},{"_id":"5033d59236c9ae856a0002ee","created_at":1345574290,"health":26,"power":197,"type":"some_new_monster_type","updated_at":1345574291,"user_id":10840},{"_id":"5033d69036c9ae7b6a0002ea","created_at":1345574544,"health":22,"power":129,"type":"some_new_monster_type","updated_at":1345574545,"user_id":10840},{"_id":"5033d81b36c9ae946a0002c4","created_at":1345574939,"health":5,"power":677,"type":"some_new_monster_type","updated_at":1345574939,"user_id":10840},{"_id":"5033da9236c9ae7f6a000360","created_at":1345575570,"health":72,"power":4,"type":"some_new_monster_type","updated_at":1345575571,"user_id":10840}]
08-21 19:59:29.361: DEBUG/QuickBlox Rest Api Test(29129): Retrieve filtered custom object instances
08-21 19:59:29.361: DEBUG/QuickBlox Rest Api Test(29129): [{"_id":"5033d38936c9ae8e6a0002e1","health":95,"power":515,"type":"type0","user_id":10840,"created_at":1345573769,"updated_at":1345573769},{"_id":"5033da9236c9ae7f6a000360","created_at":1345575570,"health":72,"power":4,"type":"some_new_monster_type","updated_at":1345575571,"user_id":10840}]
```