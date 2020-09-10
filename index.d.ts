// Type definitions for RNCrypton
// Project: react-native-crypton
// Definitions by: amirhosein heidari https://www.linkedin.com/in/amirhuusein-heidari-145292a3


export as namespace Crypton;

/*~ If this module has methods, declare them as functions like so.
 */
export interface someType {
    type: string;
    length: number;
    extras?: string[];
}
type HashType = 
    'MD5'
    |'SHA_1'
    |'SHA_224'
    |'SHA_256'
    |'SHA_384'
    |'SHA_512';
type AEStype = 
    'CBC/NoPadding'
    |'CBC/PKCS5Padding'
    //|'ECB/NoPadding'
    // |'ECB/PKCS5Padding';
type Action = 'encrypt'|'decrypt'


export function Hash(text: string , Type: HashType):Promise<string>;
export function AES(config:{text: string , key?: string , iv?: string, action: Action  ,mode?: AEStype}):Promise<string>;

/*~ You can declare types that are available via importing the module */

/// 
/**
 * random byte: generate random byte ,return Base64 
 * @param length number and must be greater than 0 
 */
export function randomByte(length:number , base64:boolean): Promise<string>;

function pbkdf2_e(text: string ,password: string ):Promise<string>
function pbkdf2_ec(text: string ,password: string , key_lenght: number, key_iteration: number ):Promise<string>
function pbkdf2_d(encrypted_text: string,password: string):Promise<string>
export const Pbkdf2 = {
    encrypt: pbkdf2_e ,
    decrypt: pbkdf2_d
    // encryptWithCustomKey: pbkdf2_ec TODO: must test

}

/*~ You can declare properties of the module using const, let, or var */
// export const myField: number;