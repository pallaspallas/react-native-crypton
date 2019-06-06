
import { NativeModules } from 'react-native';

const { RNCrypton } = NativeModules;

//AES_CBC_256
const AES_CBC_256 = {
    Encrypt:function(Plain_text, Key, iv){ return RNCrypton.AES_CBC_256_encryption(Plain_text ,Key ,iv)},
    Decrypt:function(Encrypted_text ,key ,iv){ return RNCrypton.AES_CBC_256_decryption(Encrypted_text ,key , iv)}
}


//AES_256_pbkdf2
const AES_256_pbkdf2 ={
    Decrypt:function(Encrypted_text, Password, splitter){ return RNCrypton.AES_CBC_256_pbkdf2_Decrypt(Encrypted_text, Password, splitter) },
    Encrypt:function(Plain_text, Password, splitter){return RNCrypton.AES_CBC_256_pbkdf2_Encrypt(Plain_text, Password, splitter)}
}

//=============== HASHING ================
const md5 = function(String){ return RNCrypton.MD5(String) }



const mudoles = {
    //Answer:function(message){ return RNCrypton.Answer(message) },
    AES_256_pbkdf2:AES_256_pbkdf2,
    AES_CBC_256:AES_CBC_256,
    MD5 : md5
}


export default mudoles;
