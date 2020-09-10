const im = ' is missing from config object or incorrect' 
const cr = 'Crypton: '
const isi = ' from config object is incorrect'
const pbk = 'passeord ,iv or salt'
export default errors = {
    hash_type:              cr+'type of hash is not supported!\navailable types is: MD5 , SHA_1 , SHA_224 , SHA_256 , SHA_384 , SHA_512',
    configs:                cr+'config is empty or not correct',
    config_mode:            cr+'[mode]'+im,
    config_text:            cr+'[text]'+im,
    config_action:          cr+'action'+im,
    config_action_empty:    cr+'action'+isi,
    config_KIV:             cr+'key or iv'+im,
    config_KIV_incorrect:   cr+'key or iv'+isi,
    pbk_req:                cr+pbk+im,
    pbk_req_incorrect:      cr+pbk+isi,
    main: 'RNcrypton error: ',
    random_byte: ' [ random_byte() ] ',
    pbk_encrypt: ' [ pbkdf2.encrypt() ] ',
    pbk_decrypt: ' [ pbkdf2.decrypt() ] ',
    pbk_encryptWc: ' [ pbkdf2.encryptWithCustomKey() ] ',
}
