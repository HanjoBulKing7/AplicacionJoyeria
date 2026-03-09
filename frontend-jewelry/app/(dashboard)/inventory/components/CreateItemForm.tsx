import { ItemStatus } from '@/domain/inventory';
import * as ImagePicker from 'expo-image-picker';
import { useState } from 'react';
import { Alert, Image, Pressable, StyleSheet, Switch, Text, View } from 'react-native';
import { TextInput } from 'react-native-paper';


type CreateItemFormProps = {
  onClose: () => void;
};

let heighFormContainer = 590;

export default function CreateItemForm({ onClose }: CreateItemFormProps) {

        const [ name, setName ] = useState('')
        const [ description, setDescription ] = useState('')
        const [ stock , setStock ] = useState('')
        const [ price , setPrice ] = useState('')
        const [ status , setStatus ] = useState<ItemStatus>(ItemStatus.ACTIVE)
        const [ imageUri , setImageUri ] = useState<string | null>(null)
        const [ isEnabled, setIsEnabled ] = useState(true)

        const toggleSwitch = () =>{
            setIsEnabled ( previous => !previous),
            setStatus ( prev => prev === ItemStatus.ACTIVE ? ItemStatus.INACTIVE : ItemStatus.ACTIVE )

        } 

        // Options to save  photos
        const openImageOptions = () => {
        Alert.alert(
                "Seleccionar imagen",
                "Elige una opción",
                [
                { text: "Tomar foto", onPress: takePhoto },
                { text: "Elegir de galería", onPress: pickImage },
                { text: "Cancelar", style: "cancel" }
                ]
            );
        };

        const requestCameraPermission = async () => {
            const permission = await ImagePicker.requestCameraPermissionsAsync();

            if (!permission.granted) {
                alert("Necesitamos acceso a la cámara");
                return false;
            }
            return true;
        };

        //Take photo 
        const takePhoto = async () => {

            const hasPermission = await requestCameraPermission();

            if (!hasPermission) return;
                const result = await ImagePicker.launchCameraAsync({
                mediaTypes: ['images'],
                quality: 0.8
            });

            if (!result.canceled) {
                const uri = result.assets[0].uri;
                setImageUri(uri);
            }
        };

        // Pick image from device
        const pickImage = async () => {
            const result = await ImagePicker.launchImageLibraryAsync({
                mediaTypes: ['images'],
                quality: 0.8
            });

            if (!result.canceled) {
                const uri = result.assets[0].uri;
                setImageUri(uri);
            }
        };

        // Remove Image
        const removeImage = () => { setImageUri(null); };

        type CreateItemFormProps = {
            onClose: () => void
            onSubmit: (item: InsertJewel) => void
        }

        //
        const handleSubmit = () => {
            onSubmit({
                id: crypto.randomUUID(),
                name,
                description,
                category,
                stock: Number(stock),
                price: Number(price),
                status,
                image_url: imageUri
            });

            onClose();
        };
    return (
        <View style = { styles.formContainer } >

            <Pressable onPress = { onClose}>
                <Text style = { styles.cancelText }>Cancelar</Text>
            </Pressable>
            
            <TextInput 
                style = { styles.basicInput }
                label='Nombre de la joya'
                value={ name }
                placeholder='Anillo con zirconia...'
                onChangeText={ setName } 
            />

            <TextInput 
                style = { styles.basicInput }
                label="Description" 
                value= { description}
                placeholder= 'Anillo medida 9'
                onChangeText={ setDescription } 
            />

            <TextInput 
                style = { styles.basicInput }
                label="Stock" 
                value= { stock }
                placeholder= '10'
                onChangeText={ setStock } 
                keyboardType= 'numeric'
            />

            <TextInput 
                style = { styles.basicInput }
                label="Precio" 
                value= { price }
                placeholder= '100.60'
                onChangeText={ setPrice } 
                keyboardType= 'numeric'
            />

            <Text style = { styles.statusLabel } >Estado: </Text>

            <View style = { styles.statusContainer }>
                <Text style = { status=== ItemStatus.ACTIVE ? styles.active : styles.inactive } >
                    { isEnabled ? "ACTIVO": "INACTIVO" }
                </Text>
                <Switch 
                    trackColor={{false: '#767577', true: '#81b0ff'}}
                    thumbColor={isEnabled ? '#f5dd4b' : '#f4f3f4'}
                    ios_backgroundColor="#3e3e3e"
                    onValueChange={toggleSwitch}
                    value={isEnabled}
                />
            </View>

            <View style={styles.imageContainer}>

                {imageUri ? 
                (
                    <>
                        <Image
                            source={{ uri: imageUri }}
                            style={styles.previewImage}
                        />

                        <Pressable onPress={removeImage}>
                            <Text style={styles.deleteText}>Eliminar foto</Text>
                        </Pressable>
                    </>
                ) : (
                    <>
                        <Image source={ require('../../../../assets/icons/actions/pick-image.png')} style = { styles.pickImageIcon } />
                        <Pressable onPress={openImageOptions}>
                            <Text style={styles.addImageText}>Agregar imagen</Text>
                        </Pressable>
                    </>
                )}

                </View>

                <Pressable>
                    Guardar producto
                </Pressable>
        </View>
        
    );

}

const styles = StyleSheet.create ({

    formContainer: {
        backgroundColor: '#141313',
        borderRadius: 10,
        borderWidth: 0.4,
        borderColor: '#FFFFFF',
        
        width: 280,
        height: 640,

        flexDirection: 'column',
        alignItems: 'center',
        gap: 20,
    },

    cancelText: {
        paddingTop: 20,
        color: '#FFFFFF'
    },

    basicInput: {
        height: 50,
        width: 200,
    },

    statusLabel: {
        color: '#FFFFFF',
        fontSize: 20,
    },

    statusContainer: {
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'space-between',
        marginBottom: 10,
    },

    active: {
        width: 60,
        color: '#fcff32',
        fontStyle: 'italic',
    },

    inactive: {
        width: 60,
        color: '#ff4343',
        fontStyle: 'italic'
    },

    pickImageIcon: {
        height: 30,
        width: 30,
    },

    imageContainer: {
        width: 120,
        height: 120,
        borderRadius: 10,
        borderWidth: 1,
        borderColor: "#888",
        justifyContent: "center",
        alignItems: "center"
    },

    previewImage: {
        width: 150,
        height: 150,
        borderRadius: 10,
    },

    deleteText: {
        marginTop: 20,
        color: "#ff4444",
        fontSize: 12,
    },

    addImageText: {
        color: "#aaa"
    }
});