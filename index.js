
import { NativeModules } from 'react-native';

const { RNCrypton } = NativeModules;

//AES_CBC_256
const AES_CBC_256 = {
    Encrypt:function(Plain_text, Key, iv){ return RNCrypton.AES_CBC_256_encryption(Plain_text ,Key ,iv)}
}

//AES_256_pbkdf2
const AES_256_pbkdf2 ={
    Decrypt:function(Encrypted_text , Password){ return RNCrypton.AES_CBC_256_pbkdf2_Decrypt(Encrypted_text, Password) }
}

//ALL
const mudoles = {
    Answer:function(message){ return RNCrypton.Answer(message) },
    AES_256_pbkdf2:AES_256_pbkdf2,
    AES_CBC_256:AES_CBC_256
}
export default mudoles;
