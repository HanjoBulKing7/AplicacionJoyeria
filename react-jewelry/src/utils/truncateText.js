const textLimit = 20;
const truncateText = (text) => {
    if(text.length > textLimit )
        return text.substring(0 , textLimit).concat('...')
}

export default truncateText;