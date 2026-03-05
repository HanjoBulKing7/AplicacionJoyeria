import { ItemStatus } from '@/domain/inventory';
import { useState } from 'react';
import { Pressable, StyleSheet, Switch, Text, View } from 'react-native';
import { TextInput } from 'react-native-paper';

type CreateItemFormProps = {
  onClose: () => void;
};

export default function CreateItemForm({ onClose }: CreateItemFormProps) {

     /*id TEXT PRIMARY KEY NOT NULL,
      name TEXT NOT NULL,
      description TEXT,
      category TEXT NOT NULL,
      stock INTEGER NOT NULL DEFAULT 0,
      price REAL NOT NULL DEFAULT 0,
      status TEXT NOT NULL DEFAULT 'active',
      image_url TEXT,
      created_at TEXT DEFAULT (datetime('now')),
      updated_at TEXT DEFAULT (datetime('now')
      */

        const [ name, setName ] = useState('')
        const [ description, setDescription ] = useState('')
        const [ stock , setStock ] = useState('')
        const [ price , setPrice ] = useState('')
        const [ status , setStatus ] = useState<ItemStatus>(ItemStatus.ACTIVE)
        const [ imageUri , setImageUri ] = useState('')
        const [ isEnabled, setIsEnabled ] = useState(true)

        const toggleSwitch = () =>{
            setIsEnabled ( previous => !previous),
            setStatus ( prev => prev === ItemStatus.ACTIVE ? ItemStatus.INACTIVE : ItemStatus.ACTIVE )
        } 
        

    return (
        <View style = { styles.formContainer}>

            <Pressable onPress = { onClose}>
                <Text>X</Text>
            </Pressable>
            
            <TextInput 
                style = { styles.basicInput }
                label="Name" 
                value={ name }
                placeholder='Nombre de la joya'
                onChangeText={ setName } 
                mode="outlined"
            />

            <TextInput 
                style = { styles.basicInput }
                label="Description" 
                value= { description}
                placeholder= 'Descripción de la joya'
                onChangeText={ setDescription } 
                mode="outlined"
            />

            <TextInput 
                style = { styles.basicInput }
                label="Stock" 
                value= { stock }
                placeholder= 'Cantidad en stock inicial'
                onChangeText={ setStock } 
                mode="outlined"
            />

            <Switch 
                style = { styles.basicInput }
                trackColor={{false: '#767577', true: '#81b0ff'}}
                thumbColor={isEnabled ? '#f5dd4b' : '#f4f3f4'}
                ios_backgroundColor="#3e3e3e"
                onValueChange={toggleSwitch}
                value={isEnabled}
            />

        </View>
    );

}

const styles = StyleSheet.create ({

    formContainer: {
        backgroundColor: '#585757',
        borderRadius: 10,
        borderWidth: 1,
        borderColor: '#FFFFFF',
        
        width: 300,
        height: 400,

        flexDirection: 'column',
        alignItems: 'center',
        gap: 10,
    },

    basicInput: {
        height: 50,
        width: 100,
    },
});