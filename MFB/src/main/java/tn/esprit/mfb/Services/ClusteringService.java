package tn.esprit.mfb.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
@Service
@RequiredArgsConstructor
public class ClusteringService {






    public void segmentation() throws Exception {
        // Charger les données à partir d'un fichier ARFF (ou tout autre format pris en charge par Weka)
        //DataSource source = new DataSource("C:\\Users\\USER\\Desktop\\dataset\\lesrevenus.csv");
        DataSource source = new DataSource("C:\\Users\\USER\\Desktop\\dataset\\exportt.csv");

        Instances data = source.getDataSet();

        // Créer un modèle de clustering (K-means avec k=3)
        SimpleKMeans kmeans = new SimpleKMeans();
        kmeans.setNumClusters(3);
        kmeans.buildClusterer(data);

        // Afficher les résultats de la segmentation
        for (int i = 0; i < data.numInstances(); i++) {
            int cluster = kmeans.clusterInstance(data.instance(i));
            System.out.println("Client " + (i + 1) + " est dans le cluster " + (cluster + 1));
        }
    }
}
